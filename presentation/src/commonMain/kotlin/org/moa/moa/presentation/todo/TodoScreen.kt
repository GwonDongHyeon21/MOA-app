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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.moa.domain.model.response.TodoItemResponse
import kotlinx.datetime.Clock
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import moa.presentation.generated.resources.Res
import moa.presentation.generated.resources.add
import moa.presentation.generated.resources.calendar_button_icon
import moa.presentation.generated.resources.filed_arrow_bottom
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import org.moa.moa.presentation.component.MOAErrorScreen
import org.moa.moa.presentation.todo.TodoDimens.CONTENT_LENGTH
import org.moa.moa.presentation.todo.TodoDimens.backgroundRoundedCornerShape
import org.moa.moa.presentation.todo.TodoDimens.buttonRoundedCornerShape
import org.moa.moa.presentation.todo.TodoDimens.datePickerRoundedCornerShape
import org.moa.moa.presentation.todo.TodoDimens.textInputRoundedCornerShape
import org.moa.moa.presentation.todo.TodoDimens.topPadding
import org.moa.moa.presentation.todo.TodoDimens.verticalPadding
import org.moa.moa.presentation.todo.component.MonthsDropDown
import org.moa.moa.presentation.todo.component.TodoCalendarDialog
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
import org.moa.moa.presentation.util.formatDateTime

private object TodoDimens {
    const val CONTENT_LENGTH = 100

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
            onContentChanged = { content -> viewModel.changeContent(content) },
            onDateSelected = { date -> viewModel.changeDate(date) },
            onAddTodo = { viewModel.addTodo() },
            onDoneChanged = { id, done -> viewModel.changeDone(id, done) },
            onTodoDelete = { id -> viewModel.deleteTodo(id) }
        )

        TodoScreenState.ERROR -> MOAErrorScreen(modifier = Modifier)
    }
}

@Composable
private fun TodoScreen(
    uiState: TodoUiState,
    onContentChanged: (String) -> Unit,
    onDateSelected: (LocalDate) -> Unit,
    onAddTodo: () -> Unit,
    onDoneChanged: (String, Boolean) -> Unit,
    onTodoDelete: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WHITE)
            .padding(bottom = BOTTOM_PADDING_CENTER + verticalPadding)
    ) {
        TodoTopBarSection(
            modifier = Modifier.fillMaxWidth(),
            date = uiState.date,
            todos = uiState.todos,
            yearMonths = uiState.yearMonths,
            onYearMonthSelected = { date -> onDateSelected(date) },
        )

        TodoDatePickerSection(
            modifier = Modifier.fillMaxWidth(),
            date = uiState.date,
            monthDates = uiState.monthDates,
            onDateSelected = { date -> onDateSelected(date) }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = verticalPadding, horizontal = APP_HORIZONTAL_PADDING1)
                .clip(backgroundRoundedCornerShape)
                .background(IVORY3)
                .padding(vertical = verticalPadding, horizontal = APP_HORIZONTAL_PADDING1)
        ) {
            TodoInputSection(
                modifier = Modifier.fillMaxWidth(),
                content = uiState.content,
                onContentChanged = { onContentChanged(it) },
                onAddTodo = { onAddTodo() }
            )

            Spacer(modifier = Modifier.height(20.dp))
            if (uiState.dateTodos.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val formattedDate = formatDateTime(
                        dateTime = LocalDateTime(
                            uiState.date.year,
                            uiState.date.month,
                            uiState.date.dayOfMonth,
                            0,
                            0
                        ),
                        pattern = Strings.date_month_date
                    )

                    Text(
                        text = formattedDate,
                        color = GRAY2
                    )
                    Text(
                        text = Strings.todo_placeholder,
                        color = GRAY2
                    )
                }
            } else {
                LazyColumn {
                    items(uiState.dateTodos) { todo ->
                        TodoItem(
                            modifier = Modifier.fillMaxWidth(),
                            todo = todo,
                            onDoneChanged = { onDoneChanged(todo.id, todo.done) },
                            onTodoDelete = { onTodoDelete(todo.id) }
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun TodoTopBarSection(
    modifier: Modifier,
    date: LocalDate,
    todos: List<TodoItemResponse>,
    yearMonths: List<LocalDate>,
    onYearMonthSelected: (LocalDate) -> Unit,
) {
    var dropDownExpanded by remember { mutableStateOf(false) }
    var calendarExpanded by remember { mutableStateOf(false) }

    val formattedDate = formatDateTime(
        dateTime = LocalDateTime(date.year, date.month, 1, 0, 0),
        pattern = Strings.date_month
    )

    Row(
        modifier = modifier
            .padding(horizontal = APP_HORIZONTAL_PADDING1)
            .padding(top = topPadding),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.clickable(
                interactionSource = null,
                indication = null,
                onClick = { dropDownExpanded = !dropDownExpanded }
            ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Text(
                text = formattedDate,
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
            dropDownExpanded = dropDownExpanded,
            onDropDownExpanded = { dropDownExpanded = false },
            onYearMonthSelected = { date -> onYearMonthSelected(date) }
        )

        Icon(
            painter = painterResource(Res.drawable.calendar_button_icon),
            contentDescription = "OpenCalendar",
            modifier = Modifier.clickable(
                interactionSource = null,
                indication = null,
                onClick = { calendarExpanded = true }
            )
        )
    }

    if (calendarExpanded) {
        TodoCalendarDialog(
            date = date,
            todos = todos,
            onMonthChange = { datePeriod -> onYearMonthSelected(date.plus(DatePeriod(months = datePeriod))) },
            onDateClicked = { onYearMonthSelected(it) },
            onDismissRequest = { calendarExpanded = false }
        )
    }
}

@Composable
fun TodoDatePickerSection(
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
                    text = if (isToday) Strings.todayEn else thisDate.dayOfMonth.toString(),
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

@Composable
fun TodoInputSection(
    modifier: Modifier,
    content: String,
    onContentChanged: (String) -> Unit,
    onAddTodo: () -> Unit,
) {
    Row(
        modifier = modifier
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
            value = content,
            onValueChange = { if (it.length < CONTENT_LENGTH) onContentChanged(it) },
            modifier = Modifier.weight(1f),
            placeholder = {
                Text(
                    text = Strings.todo_input_placeholder,
                    color = GRAY2
                )
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            maxLines = 1,
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