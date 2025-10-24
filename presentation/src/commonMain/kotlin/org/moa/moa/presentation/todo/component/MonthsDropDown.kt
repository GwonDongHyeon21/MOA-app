package org.moa.moa.presentation.todo.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.moa.moa.presentation.ui.theme.GRAY3
import org.moa.moa.presentation.ui.theme.Strings
import org.moa.moa.presentation.util.formatDateTime

@Composable
fun MonthsDropDown(
    yearMonths: List<LocalDate>,
    dropDownExpanded: Boolean,
    onDropDownExpanded: () -> Unit,
    onYearMonthSelected: (LocalDate) -> Unit,
) {
    val listState = rememberLazyListState()

    val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

    LaunchedEffect(dropDownExpanded) {
        if(dropDownExpanded) {
            val currentIndex = yearMonths.indexOfFirst {
                it.year == today.year && it.monthNumber == today.monthNumber
            }
            listState.scrollToItem(currentIndex)
        }
    }

    DropdownMenu(
        expanded = dropDownExpanded,
        onDismissRequest = { onDropDownExpanded() },
    ) {
        LazyColumn(
            modifier = Modifier.requiredSize(width = 140.dp, height = 200.dp),
            state = listState
        ) {
            items(yearMonths) { date ->
                val formattedDate = formatDateTime(
                    dateTime = LocalDateTime(date.year, date.month, 1, 0, 0),
                    pattern = Strings.date_year_month
                )

                Text(
                    text = formattedDate,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable {
                            onYearMonthSelected(date)
                            onDropDownExpanded()
                        },
                    textAlign = TextAlign.Center
                )
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 9.dp),
                    color = GRAY3
                )
            }
        }
    }
}