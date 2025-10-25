package org.moa.moa.presentation.calendar.calendar.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.moa.domain.model.response.Diary
import kotlinx.datetime.DayOfWeek
import org.jetbrains.compose.resources.painterResource
import org.moa.moa.presentation.calendar.calendar.model.DayInfo
import org.moa.moa.presentation.home.home.model.Emotion.Companion.toEmotion
import org.moa.moa.presentation.ui.theme.BLUE
import org.moa.moa.presentation.ui.theme.GRAY8
import org.moa.moa.presentation.ui.theme.RED2
import org.moa.moa.presentation.util.emotionRes

@Composable
fun DayCell(
    modifier: Modifier,
    dayInfo: DayInfo,
    onDateClicked: (Diary?) -> Unit,
) {
    val dateColor = if (!dayInfo.isCurrentMonth) {
        GRAY8
    } else {
        when {
            dayInfo.date.dayOfWeek == DayOfWeek.SUNDAY -> RED2
            dayInfo.date.dayOfWeek == DayOfWeek.SATURDAY -> BLUE
            dayInfo.isToday -> MaterialTheme.colorScheme.primary
            dayInfo.isCurrentMonth -> MaterialTheme.colorScheme.onSurface
            else -> GRAY8
        }
    }

    Column(
        modifier = modifier.clickable(
            indication = null,
            interactionSource = null,
            onClick = { onDateClicked(dayInfo.diaries?.firstOrNull()) }
        ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = dayInfo.date.dayOfMonth.toString(),
            color = dateColor,
            fontSize = 17.sp,
        )

        dayInfo.diaries?.firstOrNull()?.emotion.toEmotion()?.let { emotion ->
            Image(
                painter = painterResource(emotionRes(emotion)),
                contentDescription = null,
            )
        }
    }
}