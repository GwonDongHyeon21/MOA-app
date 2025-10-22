package org.moa.moa.presentation.calendar.calendar.model

import com.moa.domain.model.response.Diary
import kotlinx.datetime.LocalDate

data class DayInfo(
    val date: LocalDate,
    val isToday: Boolean,
    val isCurrentMonth: Boolean,
    val diaries: List<Diary>?,
)