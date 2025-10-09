package org.moa.moa.presentation.calendar.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import org.moa.moa.presentation.calendar.model.DayInfo
import org.moa.moa.presentation.record.model.Record
import org.moa.moa.presentation.ui.theme.GRAY8
import org.moa.moa.util.emotionRes

@Composable
fun DayCell(
    modifier: Modifier,
    dayInfo: DayInfo,
    onClickDay: (Record?) -> Unit,
) {
    val dateColor = when {
        dayInfo.isToday -> MaterialTheme.colorScheme.primary
        dayInfo.isCurrentMonth -> MaterialTheme.colorScheme.onSurface
        else -> GRAY8
    }

    Column(
        modifier = modifier.clickable(
            indication = null,
            interactionSource = null,
            onClick = { onClickDay(dayInfo.record) }
        ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = dayInfo.date.dayOfMonth.toString(),
            color = dateColor,
            fontSize = 17.sp,
        )

        dayInfo.record?.emotion?.let { emotion ->
            Image(
                painter = painterResource(emotionRes(emotion)),
                contentDescription = null,
            )
        }
    }
}