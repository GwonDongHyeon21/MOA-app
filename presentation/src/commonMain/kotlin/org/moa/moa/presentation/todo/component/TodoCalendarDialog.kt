package org.moa.moa.presentation.todo.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.moa.domain.model.response.TodoItemResponse
import kotlinx.datetime.Clock
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import moa.presentation.generated.resources.Res
import moa.presentation.generated.resources.left_arrow_icon
import moa.presentation.generated.resources.right_arrow_icon
import org.jetbrains.compose.resources.painterResource
import org.moa.moa.presentation.todo.component.TodoCalendarDialogDimens.DIALOG_HEIGHT_FRACTION
import org.moa.moa.presentation.todo.component.TodoCalendarDialogDimens.dialogRoundedCornerShape
import org.moa.moa.presentation.todo.model.TodoDayInfo
import org.moa.moa.presentation.ui.theme.APP_HORIZONTAL_PADDING1
import org.moa.moa.presentation.ui.theme.GRAY1
import org.moa.moa.presentation.ui.theme.GRAY5
import org.moa.moa.presentation.ui.theme.GRAY8
import org.moa.moa.presentation.ui.theme.IVORY
import org.moa.moa.presentation.ui.theme.IVORY3
import org.moa.moa.presentation.ui.theme.Strings
import org.moa.moa.util.buildMonthCells
import org.moa.moa.util.formatDateTime

private object TodoCalendarDialogDimens {
    const val DIALOG_HEIGHT_FRACTION = 0.8f

    val dialogRoundedCornerShape = RoundedCornerShape(20.dp)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoCalendarDialog(
    date: LocalDate,
    todos: List<TodoItemResponse>,
    onMonthChange: (Int) -> Unit,
    onDateClicked: (LocalDate) -> Unit,
    onDismissRequest: () -> Unit,
) {
    val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date

    BasicAlertDialog(
        onDismissRequest = { onDismissRequest() },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxHeight(DIALOG_HEIGHT_FRACTION)
                    .fillMaxWidth()
                    .clip(dialogRoundedCornerShape)
                    .background(IVORY3)
                    .padding(APP_HORIZONTAL_PADDING1)
            ) {
                TodoCalendarDialogHeader(
                    date = date,
                    onMonthChange = { datePeriod -> onMonthChange(datePeriod) }
                )

                remember(date.year, date.month) {
                    buildMonthCells(
                        year = date.year,
                        month = date.month,
                        startOn = DayOfWeek.SUNDAY,
                        items = todos,
                        selector = { todo -> todo.date },
                        mapper = { thisDate, todos ->
                            TodoDayInfo(
                                date = thisDate,
                                isToday = (thisDate == today),
                                isCurrentMonth = (thisDate.month == date.month),
                                todos = todos
                            )
                        }
                    )
                }.chunked(7).forEach { week ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        week.forEach { dayInfo ->
                            TodoDayCell(
                                modifier = Modifier.weight(1f),
                                dayInfo = dayInfo,
                                onDateClicked = { date ->
                                    onDateClicked(date)
                                    onDismissRequest()
                                }
                            )
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun TodoCalendarDialogHeader(
    date: LocalDate,
    onMonthChange: (Int) -> Unit,
) {
    val headerDate = formatDateTime(
        dateTime = LocalDateTime(date.year, date.month, 1, 0, 0),
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
fun TodoDayCell(
    modifier: Modifier,
    dayInfo: TodoDayInfo,
    onDateClicked: (LocalDate) -> Unit,
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
            onClick = { onDateClicked(dayInfo.date) }
        ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = dayInfo.date.dayOfMonth.toString(),
            color = dateColor,
            fontSize = 17.sp,
        )

        dayInfo.todos?.let { todos ->
            LazyColumn(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                items(todos) { todo ->
                    val backgroundColor = if (todo.done) IVORY else GRAY5

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(dialogRoundedCornerShape)
                            .padding(horizontal = 2.dp)
                            .background(backgroundColor),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = todo.content,
                            color = GRAY1,
                            fontSize = 11.sp,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1
                        )
                    }
                }
            }
        }
    }
}