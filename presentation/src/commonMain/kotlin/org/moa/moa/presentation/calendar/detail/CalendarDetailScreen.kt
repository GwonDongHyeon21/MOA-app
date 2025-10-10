package org.moa.moa.presentation.calendar.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import kotlinx.datetime.LocalDate
import kotlinx.datetime.atTime
import moa.presentation.generated.resources.Res
import moa.presentation.generated.resources.left_arrow_icon
import moa.presentation.generated.resources.right_arrow_icon
import moa.presentation.generated.resources.top_logo
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import org.moa.moa.presentation.calendar.detail.CalendarDetailDimens.CONTENT_HEIGHT_FRACTION
import org.moa.moa.presentation.calendar.detail.CalendarDetailDimens.CONTENT_IMAGE_HEIGHT_FRACTION
import org.moa.moa.presentation.calendar.detail.CalendarDetailDimens.ContentHorizontalPadding
import org.moa.moa.presentation.calendar.detail.CalendarDetailDimens.HEADER_WIDTH_FRACTION
import org.moa.moa.presentation.calendar.detail.CalendarDetailDimens.HeaderRoundedCornerShape
import org.moa.moa.presentation.calendar.detail.component.CalendarDetailContentBackground
import org.moa.moa.presentation.calendar.detail.component.CalendarDetailContentPlaceholder
import org.moa.moa.presentation.component.MOABackTopBar
import org.moa.moa.presentation.record.model.Record
import org.moa.moa.presentation.ui.theme.APP_HORIZONTAL_PADDING1
import org.moa.moa.presentation.ui.theme.CORNER_RADIUS
import org.moa.moa.presentation.ui.theme.GRAY1
import org.moa.moa.presentation.ui.theme.GRAY3
import org.moa.moa.presentation.ui.theme.GRAY4
import org.moa.moa.presentation.ui.theme.Strings
import org.moa.moa.presentation.ui.theme.WHITE
import org.moa.moa.util.emotionRes
import org.moa.moa.util.formatDateTime

object CalendarDetailDimens {
    const val HEADER_WIDTH_FRACTION = 0.6f
    const val CONTENT_HEIGHT_FRACTION = 0.8f
    const val CONTENT_IMAGE_HEIGHT_FRACTION = 0.4f
    const val CONTENT_PLACEHOLDER_HEIGHT_FRACTION = 0.5f

    val CalendarDetailRoundedCorner = RoundedCornerShape(25.dp)
    val HeaderRoundedCornerShape = RoundedCornerShape(33.dp)
    val ContentHorizontalPadding = 40.dp
}

@Composable
fun CalendarDetailScreen(
    date: String,
    onBack: () -> Unit,
    viewModel: CalendarDetailViewModel = koinInject(),
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.inputDate(date)
    }

    LaunchedEffect(uiState.date) {
        uiState.date?.let { viewModel.loadRecord(it) }
    }

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
                .padding(APP_HORIZONTAL_PADDING1),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            uiState.date?.let { date ->
                CalendarDetailHeaderSection(
                    modifier = Modifier,
                    date = date,
                    onChangeDay = { viewModel.changeDay(it) }
                )
            }

            uiState.record?.let { record ->
                Spacer(modifier = Modifier.height(15.dp))
                CalendarDetailContentSection(
                    modifier = Modifier,
                    record = record
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
    val headerDate = formatDateTime(
        dateTime = LocalDate.parse(date).atTime(0, 0),
        pattern = Strings.date_year_month_date
    )

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
    record: Record,
) {
    Box(
        modifier = modifier
            .fillMaxHeight(CONTENT_HEIGHT_FRACTION)
            .fillMaxWidth()
    ) {
        CalendarDetailContentBackground(modifier = modifier)

        Column(modifier = Modifier.padding(horizontal = ContentHorizontalPadding)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Box {
                    AsyncImage(
                        model = record.imageUrl,
                        contentDescription = "RecordImage",
                        modifier = Modifier
                            .align(Alignment.Center)
                            .fillMaxHeight(CONTENT_IMAGE_HEIGHT_FRACTION)
                            .padding(vertical = 8.dp, horizontal = 25.dp)
                            .clip(RoundedCornerShape(CORNER_RADIUS))
                            .background(GRAY4)
                            .border(1.dp, GRAY3, RoundedCornerShape(CORNER_RADIUS)),
                        placeholder = painterResource(Res.drawable.top_logo),
                        error = painterResource(Res.drawable.top_logo),
                        contentScale = ContentScale.Fit
                    )

                    record.emotion?.let { emotion ->
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
                text = record.content,
                modifier = Modifier.verticalScroll(rememberScrollState()),
                fontSize = 17.sp,
                color = GRAY1,
                lineHeight = 30.sp
            )
        }
    }
}