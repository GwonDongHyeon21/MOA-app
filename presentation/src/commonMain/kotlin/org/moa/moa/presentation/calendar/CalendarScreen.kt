package org.moa.moa.presentation.calendar

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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import kotlinx.coroutines.launch
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.Month
import kotlinx.datetime.atTime
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import moa.presentation.generated.resources.Res
import moa.presentation.generated.resources.left_arrow_icon
import moa.presentation.generated.resources.right_arrow_icon
import moa.presentation.generated.resources.top_logo
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import org.moa.moa.presentation.UiState
import org.moa.moa.presentation.calendar.CalendarDimens.BOTTOM_SHEET_CONTENT_HEIGHT
import org.moa.moa.presentation.calendar.CalendarDimens.CALENDAR_FRACTION
import org.moa.moa.presentation.calendar.CalendarDimens.TOTAL_DAY_CELLS
import org.moa.moa.presentation.calendar.CalendarDimens.horizontalPadding
import org.moa.moa.presentation.calendar.CalendarDimens.roundCornerShape
import org.moa.moa.presentation.calendar.CalendarDimens.sheetShadowElevation
import org.moa.moa.presentation.calendar.CalendarDimens.verticalPadding
import org.moa.moa.presentation.calendar.component.BottomSheetContentBackground
import org.moa.moa.presentation.calendar.component.BottomSheetContentPlaceholder
import org.moa.moa.presentation.calendar.component.BottomSheetDragHandle
import org.moa.moa.presentation.calendar.component.DayCell
import org.moa.moa.presentation.calendar.model.DayInfo
import org.moa.moa.presentation.component.MOABackTopBar
import org.moa.moa.presentation.component.MOAButton
import org.moa.moa.presentation.component.MOAErrorScreen
import org.moa.moa.presentation.component.MOALoadingScreen
import org.moa.moa.presentation.record.model.Record
import org.moa.moa.presentation.ui.theme.APP_HORIZONTAL_PADDING1
import org.moa.moa.presentation.ui.theme.APP_HORIZONTAL_PADDING2
import org.moa.moa.presentation.ui.theme.BOTTOM_PADDING
import org.moa.moa.presentation.ui.theme.GRAY1
import org.moa.moa.presentation.ui.theme.GRAY3
import org.moa.moa.presentation.ui.theme.GRAY4
import org.moa.moa.presentation.ui.theme.Strings
import org.moa.moa.presentation.ui.theme.WHITE
import org.moa.moa.util.emotionRes
import org.moa.moa.util.formatDateTime

@Composable
fun CalendarScreen(
    viewModel: CalendarViewModel = koinInject(),
    onNavigateToDetail: (String) -> Unit,
    onBack: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()

    when (uiState.screenState) {
        UiState.DEFAULT -> Unit
        UiState.SUCCESS -> CalendarScreen(
            uiState = uiState,
            onMonthChange = { viewModel.changeYearMonth(it) },
            onNavigateToDetail = { record -> onNavigateToDetail(record.date) },
            onBack = { onBack() }
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
    onNavigateToDetail: (Record) -> Unit,
    onBack: () -> Unit,
) {
    val sheetState = rememberStandardBottomSheetState(
        initialValue = SheetValue.Hidden,
        skipHiddenState = false
    )
    val scaffoldState = rememberBottomSheetScaffoldState(sheetState)
    val coroutineScope = rememberCoroutineScope()
    var selectedRecord by remember { mutableStateOf<Record?>(null) }

    LaunchedEffect(Unit) {
        scaffoldState.bottomSheetState.hide()
    }

    BottomSheetScaffold(
        sheetContent = {
            BottomSheetContentSection(
                modifier = Modifier,
                record = selectedRecord,
                onNavigateToDetail = { record -> onNavigateToDetail(record) }
            )
        },
        scaffoldState = scaffoldState,
        sheetPeekHeight = 0.dp,
        sheetShadowElevation = sheetShadowElevation,
        sheetDragHandle = { BottomSheetDragHandle(modifier = Modifier) },
        topBar = {
            MOABackTopBar(
                modifier = Modifier.background(WHITE),
                onBack = { onBack() }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(WHITE)
                .padding(innerPadding)
                .padding(bottom = BOTTOM_PADDING),
            verticalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight(CALENDAR_FRACTION)
                    .fillMaxWidth()
                    .padding(horizontal = APP_HORIZONTAL_PADDING1)
                    .clip(roundCornerShape)
                    .background(MaterialTheme.colorScheme.background)
                    .padding(vertical = verticalPadding * 2, horizontal = horizontalPadding),
                verticalArrangement = Arrangement.Center
            ) {
                CalendarMonthHeaderSection(
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
                    onSelectedRecord = {
                        coroutineScope.launch {
                            selectedRecord = it
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
    year: Int,
    month: Month,
    onMonthChange: (Int) -> Unit,
) {
    val headerDate = formatDateTime(
        dateTime = LocalDateTime(year, month, 1, 0, 0),
        pattern = Strings.date_year_month
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(onClick = { onMonthChange(-1) }) {
            Icon(
                painter = painterResource(Res.drawable.left_arrow_icon),
                contentDescription = "PreviousMonth"
            )
        }

        Text(
            text = headerDate,
            fontSize = 17.sp,
            fontWeight = FontWeight.SemiBold
        )


        IconButton(onClick = { onMonthChange(1) }) {
            Icon(
                painter = painterResource(Res.drawable.right_arrow_icon),
                contentDescription = "NextMonth"
            )
        }
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
    onSelectedRecord: (Record?) -> Unit,
) {
    remember(uiState.today, uiState.year, uiState.month, uiState.startOn, uiState.recordsByMonth) {
        buildMonthCells(
            year = uiState.year,
            month = uiState.month,
            startOn = uiState.startOn,
            recordsByMonth = uiState.recordsByMonth,
            mapper = { date, record ->
                DayInfo(
                    date = date,
                    isToday = (date == uiState.today),
                    isCurrentMonth = (date.month == uiState.month),
                    record = record
                )
            }
        )
    }.chunked(7).forEach { week ->
        Row(modifier = modifier.fillMaxWidth()) {
            week.forEach { dayInfo ->
                DayCell(
                    modifier = Modifier.weight(1f),
                    dayInfo = dayInfo,
                    onClickDay = { onSelectedRecord(it) }
                )
            }
        }
    }
}

@Composable
fun BottomSheetContentSection(
    modifier: Modifier,
    record: Record?,
    onNavigateToDetail: (Record) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxHeight(BOTTOM_SHEET_CONTENT_HEIGHT)
            .fillMaxWidth()
            .padding(horizontal = APP_HORIZONTAL_PADDING1)
            .padding(bottom = BOTTOM_PADDING + verticalPadding * 2),
    ) {
        record?.let {
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
                        vertical = verticalPadding,
                        horizontal = APP_HORIZONTAL_PADDING1
                    )
                )

                Column(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxSize()
                        .padding(
                            vertical = verticalPadding * 2,
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
                            model = it.imageUrl,
                            contentDescription = "record_image",
                            modifier = Modifier
                                .fillMaxHeight()
                                .fillMaxWidth(0.3f)
                                .clip(RoundedCornerShape(5.dp))
                                .background(GRAY4)
                                .border(1.dp, GRAY3, RoundedCornerShape(5.dp)),
                            placeholder = painterResource(Res.drawable.top_logo),
                            error = painterResource(Res.drawable.top_logo),
                            contentScale = ContentScale.Fit,
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

                it.emotion?.let { emotion ->
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

private fun buildMonthCells(
    year: Int,
    month: Month,
    startOn: DayOfWeek,
    recordsByMonth: List<Record>,
    mapper: (LocalDate, Record?) -> DayInfo,
): List<DayInfo> {
    val firstDateOfMonth = LocalDate(year, month, 1)
    val shift = dayDistance(startOn, firstDateOfMonth.dayOfWeek)
    val startDate = firstDateOfMonth.minus(DatePeriod(days = shift))

    return (0 until TOTAL_DAY_CELLS).map { dayDistance ->
        val date = startDate.plus(DatePeriod(days = dayDistance))
        val record = recordsByMonth.find { it.date == date.toString() }
        mapper(date, record)
    }
}

private fun dayDistance(from: DayOfWeek, to: DayOfWeek): Int {
    val fromDate = from.ordinal
    val toDate = to.ordinal
    return (toDate - fromDate + 7) % 7
}