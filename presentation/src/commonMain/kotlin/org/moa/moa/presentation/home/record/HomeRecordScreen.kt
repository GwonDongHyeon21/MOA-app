package org.moa.moa.presentation.home.record

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.datetime.LocalDate
import kotlinx.datetime.atTime
import moa.presentation.generated.resources.Res
import moa.presentation.generated.resources.emotion_bad
import moa.presentation.generated.resources.emotion_guide
import moa.presentation.generated.resources.emotion_sad
import moa.presentation.generated.resources.emotion_smile
import moa.presentation.generated.resources.emotion_soso
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import org.moa.moa.presentation.component.ContentBackground
import org.moa.moa.presentation.component.ContentImage
import org.moa.moa.presentation.component.MOABackTopBar
import org.moa.moa.presentation.component.MOAErrorScreen
import org.moa.moa.presentation.home.home.model.Emotion
import org.moa.moa.presentation.home.record.HomeRecordDimens.ContentHorizontalPadding
import org.moa.moa.presentation.home.record.HomeRecordDimens.emotionRoundedCornerShape
import org.moa.moa.presentation.home.record.component.HomeRecordLoading
import org.moa.moa.presentation.record.model.Record
import org.moa.moa.presentation.ui.theme.APP_HORIZONTAL_PADDING1
import org.moa.moa.presentation.ui.theme.BOTTOM_PADDING_CENTER
import org.moa.moa.presentation.ui.theme.GRAY1
import org.moa.moa.presentation.ui.theme.Strings
import org.moa.moa.presentation.ui.theme.WHITE
import org.moa.moa.util.emotionRes
import org.moa.moa.util.formatDateTime

private object HomeRecordDimens {
    val ContentHorizontalPadding = 40.dp
    val emotionRoundedCornerShape = RoundedCornerShape(49.dp)
}

@Composable
fun HomeRecordScreen(
    viewModel: HomeRecordViewModel = koinInject(),
    onBack: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()

    DisposableEffect(Unit) {
        onDispose {
            viewModel.decideEmotion()
        }
    }

    when (uiState.screenState) {
        HomeRecordScreenState.SUCCESS -> HomeRecordScreen(
            date = uiState.date,
            record = uiState.record,
            emotion = uiState.emotion,
            isLoading = uiState.isLoading,
            onSelectedEmotion = { emotion -> viewModel.selectEmotion(emotion) },
            onBack = { onBack() }
        )

        HomeRecordScreenState.ERROR -> MOAErrorScreen(Modifier)
    }
}

@Composable
private fun HomeRecordScreen(
    date: String,
    record: Record?,
    emotion: Emotion?,
    onSelectedEmotion: (Emotion?) -> Unit,
    onBack: () -> Unit,
    isLoading: Boolean,
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
                .padding(vertical = 15.dp, horizontal = APP_HORIZONTAL_PADDING1)
                .padding(bottom = BOTTOM_PADDING_CENTER + 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HomeRecordHeaderSection(
                modifier = Modifier,
                date = date,
            )

            Spacer(modifier = Modifier.height(20.dp))
            Box(modifier = Modifier.weight(1f)) {
                Box(modifier = Modifier.padding(bottom = 20.dp)) {
                    ContentBackground(modifier = Modifier.fillMaxSize())

                    Spacer(modifier = Modifier.height(15.dp))
                    HomeRecordSectionSection(
                        modifier = Modifier,
                        isLoading = isLoading,
                        record = record
                    )
                }

                if (emotion == null) {
                    Image(
                        painter = painterResource(Res.drawable.emotion_guide),
                        contentDescription = "EmotionGuide",
                        modifier = Modifier.align(Alignment.BottomCenter)
                    )
                }
            }

            HomeEmotionSection(
                modifier = Modifier,
                emotion = emotion,
                onSelectedEmotion = { emotion -> onSelectedEmotion(emotion) }
            )
        }
    }
}

@Composable
fun HomeRecordHeaderSection(
    modifier: Modifier,
    date: String,
) {
    val headerDate = formatDateTime(
        dateTime = LocalDate.parse(date).atTime(0, 0),
        pattern = Strings.date_year_month_date
    )

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
}

@Composable
fun HomeRecordSectionSection(
    modifier: Modifier,
    isLoading: Boolean,
    record: Record?,
) {
    if (isLoading) {
        HomeRecordLoading(modifier = modifier)
    } else {
        Column(modifier = modifier.padding(horizontal = ContentHorizontalPadding)) {
            record?.apply {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Box {
                        ContentImage(
                            modifier = Modifier.align(Alignment.Center),
                            records = imageUrl
                        )

                        emotion?.let { emotion ->
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
                    text = content,
                    modifier = Modifier.verticalScroll(rememberScrollState()),
                    fontSize = 17.sp,
                    color = GRAY1,
                    lineHeight = 30.sp
                )
            }
        }
    }
}

@Composable
fun HomeEmotionSection(
    modifier: Modifier,
    emotion: Emotion?,
    onSelectedEmotion: (Emotion?) -> Unit,
) {
    Row(
        modifier = modifier
            .background(WHITE, emotionRoundedCornerShape)
            .padding(vertical = 7.dp, horizontal = 11.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val emotions = listOf(
            Emotion.SMILE to Res.drawable.emotion_smile,
            Emotion.SOSO to Res.drawable.emotion_soso,
            Emotion.SAD to Res.drawable.emotion_sad,
            Emotion.BAD to Res.drawable.emotion_bad
        )
        emotions.forEach { (emotionType, emotionRes) ->
            val isSelected = emotion == emotionType
            val emotionAlpha = emotion?.let { if (isSelected) 1f else 0.4f } ?: 1f
            val selectedEmotion = if (isSelected) null else emotionType

            Image(
                painter = painterResource(emotionRes),
                contentDescription = emotionType.label,
                modifier = Modifier
                    .clickable(
                        indication = null,
                        interactionSource = null,
                        onClick = { onSelectedEmotion(selectedEmotion) })
                    .alpha(emotionAlpha)
            )
        }
    }
}