package org.moa.moa.presentation.todo.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.datetime.LocalDate
import org.moa.moa.presentation.ui.theme.GRAY3

@Composable
fun MonthsDropDown(
    yearMonths: List<LocalDate>,
    monthDropDownExpanded: Boolean,
    onDropDownExpanded: () -> Unit,
    onYearMonthSelected: (LocalDate) -> Unit,
) {
    DropdownMenu(
        expanded = monthDropDownExpanded,
        onDismissRequest = { onDropDownExpanded() },
    ) {
        LazyColumn(modifier = Modifier.requiredSize(width = 140.dp, height = 200.dp)) {
            items(yearMonths) { date ->
                Text(
                    text = "${date.year}년 ${date.monthNumber}월",
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