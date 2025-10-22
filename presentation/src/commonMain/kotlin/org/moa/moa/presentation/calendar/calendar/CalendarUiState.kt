package org.moa.moa.presentation.calendar.calendar

import com.moa.domain.model.response.Diary
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import org.moa.moa.presentation.UiState

data class CalendarUiState(
    val screenState: UiState,
    val today: LocalDate,
    val year: Int,
    val month: Month,
    val startOn: DayOfWeek,
    val diaries: List<Diary>,
)