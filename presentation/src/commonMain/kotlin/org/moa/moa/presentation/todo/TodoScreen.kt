package org.moa.moa.presentation.todo

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.datetime.Clock
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import moa.presentation.generated.resources.Res
import moa.presentation.generated.resources.add
import moa.presentation.generated.resources.calendar_button_icon
import moa.presentation.generated.resources.filed_arrow_bottom
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import org.moa.moa.presentation.component.MOAErrorScreen
import org.moa.moa.presentation.todo.TodoDimens.backgroundRoundedCornerShape
import org.moa.moa.presentation.todo.TodoDimens.buttonRoundedCornerShape
import org.moa.moa.presentation.todo.TodoDimens.datePickerRoundedCornerShape
import org.moa.moa.presentation.todo.TodoDimens.textInputRoundedCornerShape
import org.moa.moa.presentation.todo.TodoDimens.topPadding
import org.moa.moa.presentation.todo.TodoDimens.verticalPadding
import org.moa.moa.presentation.todo.component.MonthsDropDown
import org.moa.moa.presentation.todo.component.TodoItem
import org.moa.moa.presentation.ui.theme.APP_HORIZONTAL_PADDING1
import org.moa.moa.presentation.ui.theme.BLACK
import org.moa.moa.presentation.ui.theme.BOTTOM_PADDING_CENTER
import org.moa.moa.presentation.ui.theme.GRAY1
import org.moa.moa.presentation.ui.theme.GRAY2
import org.moa.moa.presentation.ui.theme.IVORY
import org.moa.moa.presentation.ui.theme.IVORY3
import org.moa.moa.presentation.ui.theme.Strings
import org.moa.moa.presentation.ui.theme.Strings.friday
import org.moa.moa.presentation.ui.theme.Strings.monday
import org.moa.moa.presentation.ui.theme.Strings.saturday
import org.moa.moa.presentation.ui.theme.Strings.sunday
import org.moa.moa.presentation.ui.theme.Strings.thursday
import org.moa.moa.presentation.ui.theme.Strings.tuesday
import org.moa.moa.presentation.ui.theme.Strings.wednesday
import org.moa.moa.presentation.ui.theme.WHITE
import org.moa.moa.presentation.ui.theme.transparent

private object TodoDimens {
    val backgroundRoundedCornerShape = RoundedCornerShape(20.dp)
    val textInputRoundedCornerShape = RoundedCornerShape(30.dp)
    val buttonRoundedCornerShape = RoundedCornerShape(36.dp)
    val datePickerRoundedCornerShape = RoundedCornerShape(15.dp)

    val verticalPadding = 20.dp
    val topPadding = 20.dp
}

@Composable
fun TodoScreen(viewModel: TodoViewModel = koinInject()) {
    val uiState by viewModel.uiState.collectAsState()

    when (uiState.screenState) {
        TodoScreenState.TODO -> TodoScreen(
            uiState = uiState,
            changeContent = { content -> viewModel.changeContent(content) },
            onDropDownExpanded = { viewModel.expandDropDown() },
            onDateSelected = { date -> viewModel.changeDate(date) },
            onAddTodo = { viewModel.addTodo() },
            onTodoDelete = { id -> viewModel.deleteTodo(id) }
        )

        TodoScreenState.ERROR -> MOAErrorScreen(modifier = Modifier)
    }
}

@Composable
private fun TodoScreen(
    uiState: TodoUiState,
    changeContent: (String) -> Unit,
    onDropDownExpanded: () -> Unit,
    onDateSelected: (LocalDate) -> Unit,
    onAddTodo: () -> Unit,
    onTodoDelete: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WHITE)
            .padding(bottom = BOTTOM_PADDING_CENTER + verticalPadding)
    ) {
        TodoTopBarSection(
            month = uiState.date.monthNumber,
            yearMonths = uiState.yearMonths,
            monthDropDownExpanded = uiState.monthDropDownExpanded,
            onDropDownExpanded = { onDropDownExpanded() },
            onYearMonthSelected = { date -> onDateSelected(date) },
        )

        DatePickerSection(
            modifier = Modifier.fillMaxWidth(),
            date = uiState.date,
            monthDates = uiState.monthDates,
            onDateSelected = { date -> onDateSelected(date) }
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = verticalPadding, horizontal = APP_HORIZONTAL_PADDING1)
                .clip(backgroundRoundedCornerShape)
                .background(IVORY3)
                .padding(vertical = verticalPadding, horizontal = APP_HORIZONTAL_PADDING1)
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(textInputRoundedCornerShape)
                        .border(1.dp, GRAY2, textInputRoundedCornerShape)
                        .background(WHITE)
                        .padding(horizontal = 15.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.add),
                        contentDescription = null
                    )

                    OutlinedTextField(
                        value = uiState.content,
                        onValueChange = { changeContent(it) },
                        modifier = Modifier.weight(1f),
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = transparent,
                            unfocusedIndicatorColor = transparent,
                            disabledIndicatorColor = transparent
                        )
                    )

                    Text(
                        text = Strings.complete,
                        modifier = Modifier
                            .clip(buttonRoundedCornerShape)
                            .background(IVORY)
                            .clickable { onAddTodo() }
                            .padding(vertical = 5.dp, horizontal = 10.dp)
                    )
                }
            }

            item { Spacer(modifier = Modifier.height(20.dp)) }
            items(uiState.dateTodos) { todo ->
                TodoItem(
                    modifier = Modifier.fillMaxWidth(),
                    todo = todo,
                    onTodoDelete = { onTodoDelete(todo.id) }
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
fun TodoTopBarSection(
    month: Int,
    yearMonths: List<LocalDate>,
    monthDropDownExpanded: Boolean,
    onDropDownExpanded: () -> Unit,
    onYearMonthSelected: (LocalDate) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = APP_HORIZONTAL_PADDING1)
            .padding(top = topPadding),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.clickable(
                interactionSource = null,
                indication = null,
                onClick = { onDropDownExpanded() }
            ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Text(
                text = "${month}월",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )
            Icon(
                painter = painterResource(Res.drawable.filed_arrow_bottom),
                contentDescription = "OpenMonthDialog"
            )
        }

        MonthsDropDown(
            yearMonths = yearMonths,
            monthDropDownExpanded = monthDropDownExpanded,
            onDropDownExpanded = { onDropDownExpanded() },
            onYearMonthSelected = { date -> onYearMonthSelected(date) }
        )

        Icon(
            painter = painterResource(Res.drawable.calendar_button_icon),
            contentDescription = "OpenCalendar",
            modifier = Modifier.clickable(
                interactionSource = null,
                indication = null,
                onClick = {}
            )
        )
    }
}

@Composable
fun DatePickerSection(
    modifier: Modifier,
    date: LocalDate,
    monthDates: List<LocalDate>,
    onDateSelected: (LocalDate) -> Unit,
) {
    val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
    val listState = rememberLazyListState()

    LaunchedEffect(date) {
        listState.layoutInfo.visibleItemsInfo.firstOrNull()?.let {
            val index = monthDates.indexOf(date)
            val viewportWidth = listState.layoutInfo.viewportSize.width
            val avgItemWidth = listState.layoutInfo.visibleItemsInfo
                .map { it.size }
                .average()
                .toInt()
            val centerOffset = viewportWidth / 2 - avgItemWidth / 2

            listState.animateScrollToItem(index, scrollOffset = -centerOffset)
        }
    }

    LazyRow(
        modifier = modifier,
        state = listState,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(monthDates) { thisDate ->
            val isToday = thisDate == today
            val isSelected = thisDate == date

            Column(
                modifier = Modifier
                    .clip(datePickerRoundedCornerShape)
                    .background(if (isSelected) MaterialTheme.colorScheme.primary else transparent)
                    .clickable { onDateSelected(thisDate) }
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = if (isToday) "TODAY" else thisDate.dayOfMonth.toString(),
                    fontWeight = if (isSelected) FontWeight.SemiBold else null,
                    color = if (isSelected) BLACK else GRAY1,
                    fontSize = 17.sp
                )
                Text(
                    text = when (thisDate.dayOfWeek) {
                        DayOfWeek.MONDAY -> monday
                        DayOfWeek.TUESDAY -> tuesday
                        DayOfWeek.WEDNESDAY -> wednesday
                        DayOfWeek.THURSDAY -> thursday
                        DayOfWeek.FRIDAY -> friday
                        DayOfWeek.SATURDAY -> saturday
                        DayOfWeek.SUNDAY -> sunday
                        else -> ""
                    },
                    color = if (isSelected) BLACK else GRAY2,
                    fontSize = 12.sp
                )
            }
        }
    }
}