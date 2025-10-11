package org.moa.moa.presentation.calendar.calendar

import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import org.moa.moa.presentation.UiState
import org.moa.moa.presentation.record.model.Record

data class CalendarUiState(
    val screenState: UiState,
    val today: LocalDate,
    val year: Int,
    val month: Month,
    val startOn: DayOfWeek,
    val records: List<Record>,
)