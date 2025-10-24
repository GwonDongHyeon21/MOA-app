package org.moa.moa.presentation.calendar.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.moa.domain.model.response.Diary
import kotlinx.datetime.LocalDate
import kotlinx.datetime.atTime
import moa.presentation.generated.resources.Res
import moa.presentation.generated.resources.left_arrow_icon
import moa.presentation.generated.resources.right_arrow_icon
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import org.moa.moa.presentation.UiState
import org.moa.moa.presentation.calendar.detail.CalendarDetailDimens.ContentHorizontalPadding
import org.moa.moa.presentation.calendar.detail.CalendarDetailDimens.HEADER_WIDTH_FRACTION
import org.moa.moa.presentation.calendar.detail.CalendarDetailDimens.HeaderRoundedCornerShape
import org.moa.moa.presentation.calendar.detail.component.CalendarDetailContentPlaceholder
import org.moa.moa.presentation.component.ContentBackground
import org.moa.moa.presentation.component.ContentImage
import org.moa.moa.presentation.component.MOABackTopBar
import org.moa.moa.presentation.component.MOAErrorScreen
import org.moa.moa.presentation.component.MOALoadingScreen
import org.moa.moa.presentation.home.home.model.Emotion.Companion.toEmotion
import org.moa.moa.presentation.ui.theme.APP_HORIZONTAL_PADDING1
import org.moa.moa.presentation.ui.theme.BOTTOM_PADDING_CENTER
import org.moa.moa.presentation.ui.theme.GRAY1
import org.moa.moa.presentation.ui.theme.Strings
import org.moa.moa.presentation.ui.theme.WHITE
import org.moa.moa.presentation.util.emotionRes
import org.moa.moa.presentation.util.formatDateTime

private object CalendarDetailDimens {
    const val HEADER_WIDTH_FRACTION = 0.6f

    val HeaderRoundedCornerShape = RoundedCornerShape(33.dp)
    val ContentHorizontalPadding = 40.dp
}

@Composable
fun CalendarDetailScreen(
    date: String,
    viewModel: CalendarDetailViewModel = koinInject(),
    onBack: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.findRecord(date)
    }

    when (uiState.screenState) {
        UiState.DEFAULT -> Unit
        UiState.SUCCESS -> CalendarDetailScreen(
            date = uiState.date,
            diary = uiState.diary,
            onChangeDay = { datePeriod -> viewModel.changeDay(datePeriod) },
            onBack = { onBack() }
        )

        UiState.LOADING -> MOALoadingScreen(Modifier)
        UiState.ERROR -> MOAErrorScreen(Modifier)
    }
}

@Composable
private fun CalendarDetailScreen(
    date: String,
    diary: Diary?,
    onChangeDay: (Int) -> Unit,
    onBack: () -> Unit,
) {
    Scaffold(
        topBar = {
            MOABackTopBar(
                modifier = Modifier,
                onBack = { onBack() }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(vertical = 15.dp, horizontal = APP_HORIZONTAL_PADDING1),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CalendarDetailHeaderSection(
                modifier = Modifier,
                date = date,
                onChangeDay = { datePeriod -> onChangeDay(datePeriod) }
            )

            diary?.let {
                Spacer(modifier = Modifier.height(15.dp))
                CalendarDetailContentSection(
                    modifier = Modifier,
                    diary = it
                )
            } ?: run {
                Spacer(modifier = Modifier.height(40.dp))
                CalendarDetailContentPlaceholder(modifier = Modifier)
            }
        }
    }
}

@Composable
fun CalendarDetailHeaderSection(
    modifier: Modifier,
    date: String,
    onChangeDay: (Int) -> Unit,
) {
    val headerDate = if (date.isNotEmpty()) {
        formatDateTime(
            dateTime = LocalDate.parse(date).atTime(0, 0),
            pattern = Strings.date_year_month_date
        )
    } else {
        ""
    }

    Row(
        modifier = modifier
            .fillMaxWidth(HEADER_WIDTH_FRACTION)
            .clip(HeaderRoundedCornerShape)
            .background(WHITE)
            .padding(5.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(onClick = { onChangeDay(-1) }) {
            Icon(
                painter = painterResource(Res.drawable.left_arrow_icon),
                contentDescription = "PreviousDay"
            )
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = Strings.todayRecord,
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = headerDate,
                fontSize = 12.sp
            )
        }

        IconButton(onClick = { onChangeDay(1) }) {
            Icon(
                painter = painterResource(Res.drawable.right_arrow_icon),
                contentDescription = "NextDay"
            )
        }
    }
}

@Composable
fun CalendarDetailContentSection(
    modifier: Modifier,
    diary: Diary,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = BOTTOM_PADDING_CENTER + 20.dp)
    ) {
        ContentBackground(modifier = modifier.fillMaxSize())

        Column(modifier = Modifier.padding(horizontal = ContentHorizontalPadding)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Box {
                    ContentImage(
                        modifier = Modifier.align(Alignment.Center),
                        images = diary.images
                    )

                    diary.emotion.toEmotion()?.let { emotion ->
                        Image(
                            painter = painterResource(emotionRes(emotion)),
                            contentDescription = null,
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .size(52.dp)
                                .rotate(-11f)
                        )
                    }
                }
            }

            Text(
                text = diary.content,
                modifier = Modifier.verticalScroll(rememberScrollState()),
                fontSize = 17.sp,
                color = GRAY1,
                lineHeight = 30.sp
            )
        }
    }
}