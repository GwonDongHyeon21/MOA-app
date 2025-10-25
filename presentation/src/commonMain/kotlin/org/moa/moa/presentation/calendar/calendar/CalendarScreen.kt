package org.moa.moa.presentation.calendar.calendar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.moa.domain.model.response.Diary
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.Month
import kotlinx.datetime.atTime
import moa.presentation.generated.resources.Res
import moa.presentation.generated.resources.left_arrow_icon
import moa.presentation.generated.resources.right_arrow_icon
import moa.presentation.generated.resources.top_logo
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import org.moa.moa.presentation.UiState
import org.moa.moa.presentation.calendar.calendar.CalendarDimens.BOTTOM_SHEET_CONTENT_HEIGHT
import org.moa.moa.presentation.calendar.calendar.CalendarDimens.CALENDAR_FRACTION
import org.moa.moa.presentation.calendar.calendar.CalendarDimens.buttonRoundedCornerShape
import org.moa.moa.presentation.calendar.calendar.CalendarDimens.horizontalPadding
import org.moa.moa.presentation.calendar.calendar.CalendarDimens.roundedCornerShape
import org.moa.moa.presentation.calendar.calendar.CalendarDimens.sheetShadowElevation
import org.moa.moa.presentation.calendar.calendar.CalendarDimens.sheetVerticalPadding
import org.moa.moa.presentation.calendar.calendar.CalendarDimens.verticalPadding
import org.moa.moa.presentation.calendar.calendar.component.BottomSheetContentBackground
import org.moa.moa.presentation.calendar.calendar.component.BottomSheetContentPlaceholder
import org.moa.moa.presentation.calendar.calendar.component.BottomSheetDragHandle
import org.moa.moa.presentation.calendar.calendar.component.DayCell
import org.moa.moa.presentation.calendar.calendar.model.DayInfo
import org.moa.moa.presentation.component.MOAButton
import org.moa.moa.presentation.component.MOAErrorScreen
import org.moa.moa.presentation.component.MOALoadingScreen
import org.moa.moa.presentation.component.MOATopBar
import org.moa.moa.presentation.home.home.model.Emotion.Companion.toEmotion
import org.moa.moa.presentation.ui.theme.APP_HORIZONTAL_PADDING1
import org.moa.moa.presentation.ui.theme.APP_HORIZONTAL_PADDING2
import org.moa.moa.presentation.ui.theme.BOTTOM_PADDING_CENTER
import org.moa.moa.presentation.ui.theme.GRAY1
import org.moa.moa.presentation.ui.theme.GRAY3
import org.moa.moa.presentation.ui.theme.GRAY4
import org.moa.moa.presentation.ui.theme.MAIN
import org.moa.moa.presentation.ui.theme.Strings
import org.moa.moa.presentation.ui.theme.WHITE
import org.moa.moa.presentation.ui.theme.textStyle2
import org.moa.moa.presentation.util.buildMonthCells
import org.moa.moa.presentation.util.emotionRes
import org.moa.moa.presentation.util.formatDateTime

private object CalendarDimens {
    const val CALENDAR_FRACTION = 0.8f
    val verticalPadding = 40.dp
    val horizontalPadding = 20.dp

    val roundedCornerShape = RoundedCornerShape(20.dp)
    val buttonRoundedCornerShape = RoundedCornerShape(74.dp)

    const val BOTTOM_SHEET_CONTENT_HEIGHT = 0.5f
    val sheetVerticalPadding = 20.dp
    val sheetShadowElevation = 8.dp
}

@Composable
fun CalendarScreen(
    viewModel: CalendarViewModel = koinInject(),
    onNavigateToDetail: (String) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()

    when (uiState.screenState) {
        UiState.DEFAULT -> Unit
        UiState.SUCCESS -> CalendarScreen(
            uiState = uiState,
            onMonthChange = { viewModel.changeYearMonth(it) },
            onNavigateToDetail = { diary -> onNavigateToDetail(diary.date) },
        )

        UiState.LOADING -> MOALoadingScreen(Modifier)
        UiState.ERROR -> MOAErrorScreen(Modifier)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CalendarScreen(
    uiState: CalendarUiState,
    onMonthChange: (Int) -> Unit,
    onNavigateToDetail: (Diary) -> Unit,
) {
    val sheetState = rememberStandardBottomSheetState(
        initialValue = SheetValue.Hidden,
        skipHiddenState = false
    )
    val scaffoldState = rememberBottomSheetScaffoldState(sheetState)
    val coroutineScope = rememberCoroutineScope()
    var selectedDiary by remember { mutableStateOf<Diary?>(null) }

    LaunchedEffect(Unit) {
        scaffoldState.bottomSheetState.hide()
    }

    BottomSheetScaffold(
        sheetContent = {
            BottomSheetContentSection(
                modifier = Modifier,
                diary = selectedDiary,
                onNavigateToDetail = { diary -> onNavigateToDetail(diary) }
            )
        },
        scaffoldState = scaffoldState,
        sheetPeekHeight = 0.dp,
        sheetShadowElevation = sheetShadowElevation,
        sheetDragHandle = { BottomSheetDragHandle(modifier = Modifier) },
        topBar = {
            MOATopBar(modifier = Modifier.background(WHITE))
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(WHITE)
                .padding(innerPadding)
                .padding(bottom = BOTTOM_PADDING_CENTER),
            verticalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight(CALENDAR_FRACTION)
                    .fillMaxWidth()
                    .padding(horizontal = APP_HORIZONTAL_PADDING1)
                    .clip(roundedCornerShape)
                    .background(MaterialTheme.colorScheme.background)
                    .padding(vertical = verticalPadding, horizontal = horizontalPadding),
                verticalArrangement = Arrangement.Center
            ) {
                CalendarMonthHeaderSection(
                    modifier = Modifier.fillMaxWidth(),
                    year = uiState.year,
                    month = uiState.month,
                    onMonthChange = { onMonthChange(it) }
                )

                Spacer(Modifier.height(20.dp))
                CalendarDayLabelsSection(modifier = Modifier)

                Spacer(Modifier.height(12.dp))
                CalendarDaysSection(
                    modifier = Modifier.weight(1f),
                    uiState = uiState,
                    onSelectedDiary = {
                        coroutineScope.launch {
                            selectedDiary = it
                            scaffoldState.bottomSheetState.expand()
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun CalendarMonthHeaderSection(
    modifier: Modifier,
    year: Int,
    month: Month,
    onMonthChange: (Int) -> Unit,
) {
    val headerDate = formatDateTime(
        dateTime = LocalDateTime(year, month, 1, 0, 0),
        pattern = Strings.date_year_month
    )

    Box(modifier = modifier) {
        Row(
            modifier = Modifier.align(Alignment.Center),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            IconButton(onClick = { onMonthChange(-1) }) {
                Icon(
                    painter = painterResource(Res.drawable.left_arrow_icon),
                    contentDescription = "PreviousMonth"
                )
            }

            Text(
                text = headerDate,
                style = textStyle2
            )


            IconButton(onClick = { onMonthChange(1) }) {
                Icon(
                    painter = painterResource(Res.drawable.right_arrow_icon),
                    contentDescription = "NextMonth"
                )
            }
        }

        Text(
            text = Strings.today,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .border(2.dp, MAIN, buttonRoundedCornerShape)
                .background(WHITE, buttonRoundedCornerShape)
                .clip(buttonRoundedCornerShape)
                .clickable { onMonthChange(0) }
                .padding(horizontal = 9.dp)
        )
    }
}

@Composable
fun CalendarDayLabelsSection(modifier: Modifier) {
    val dayLabels = listOf(
        Strings.sunday,
        Strings.monday,
        Strings.tuesday,
        Strings.wednesday,
        Strings.thursday,
        Strings.friday,
        Strings.saturday
    )

    Row(modifier = modifier.fillMaxWidth()) {
        dayLabels.forEach { day ->
            Text(
                text = day,
                modifier = Modifier.weight(1f),
                fontSize = 17.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun CalendarDaysSection(
    modifier: Modifier,
    uiState: CalendarUiState,
    onSelectedDiary: (Diary?) -> Unit,
) {
    remember(uiState.today, uiState.year, uiState.month, uiState.startOn, uiState.diaries) {
        buildMonthCells(
            year = uiState.year,
            month = uiState.month,
            startOn = uiState.startOn,
            items = uiState.diaries,
            selector = { diary -> diary.date },
            mapper = { date, diaries ->
                DayInfo(
                    date = date,
                    isToday = (date == uiState.today),
                    isCurrentMonth = (date.month == uiState.month),
                    diaries = diaries
                )
            }
        )
    }.chunked(7).forEach { week ->
        Row(modifier = modifier.fillMaxWidth()) {
            week.forEach { dayInfo ->
                DayCell(
                    modifier = Modifier.weight(1f),
                    dayInfo = dayInfo,
                    onDateClicked = { onSelectedDiary(it) }
                )
            }
        }
    }
}

@Composable
fun BottomSheetContentSection(
    modifier: Modifier,
    diary: Diary?,
    onNavigateToDetail: (Diary) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxHeight(BOTTOM_SHEET_CONTENT_HEIGHT)
            .fillMaxWidth()
            .padding(horizontal = APP_HORIZONTAL_PADDING1)
            .padding(bottom = BOTTOM_PADDING_CENTER + sheetVerticalPadding * 2),
    ) {
        diary?.let {
            val contentDate = formatDateTime(
                dateTime = LocalDate.parse(it.date).atTime(0, 0),
                pattern = Strings.date_year_month_date
            )

            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 10.dp)
            ) {
                BottomSheetContentBackground(
                    modifier = Modifier.padding(
                        vertical = sheetVerticalPadding,
                        horizontal = APP_HORIZONTAL_PADDING1
                    )
                )

                Column(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxSize()
                        .padding(
                            vertical = verticalPadding,
                            horizontal = APP_HORIZONTAL_PADDING1 + APP_HORIZONTAL_PADDING2
                        )
                ) {
                    Text(
                        text = contentDate,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AsyncImage(
                            model = it.images.firstOrNull(),
                            contentDescription = "record_image",
                            modifier = Modifier
                                .fillMaxHeight()
                                .fillMaxWidth(0.3f)
                                .clip(RoundedCornerShape(5.dp))
                                .background(GRAY4)
                                .border(1.dp, GRAY3, RoundedCornerShape(5.dp)),
                            placeholder = painterResource(Res.drawable.top_logo),
                            error = painterResource(Res.drawable.top_logo)
                        )

                        Spacer(modifier = Modifier.width(20.dp))
                        Text(
                            text = it.content,
                            fontSize = 12.sp,
                            color = GRAY1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                it.emotion.toEmotion()?.let { emotion ->
                    Image(
                        painter = painterResource(emotionRes(emotion)),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(5.dp)
                            .size(49.dp)
                            .align(Alignment.TopEnd)
                            .rotate(10f)
                    )
                }
            }

            MOAButton(
                modifier = Modifier.fillMaxWidth(),
                text = Strings.see_all,
                onClick = { onNavigateToDetail(it) }
            )
        } ?: run {
            BottomSheetContentPlaceholder(modifier = Modifier)
        }
    }
}