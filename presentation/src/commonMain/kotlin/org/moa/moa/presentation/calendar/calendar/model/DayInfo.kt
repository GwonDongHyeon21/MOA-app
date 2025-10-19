package org.moa.moa.presentation.calendar.calendar.model

import kotlinx.datetime.LocalDate
import org.moa.moa.presentation.record.model.Record

data class DayInfo(
    val date: LocalDate,
    val isToday: Boolean,
    val isCurrentMonth: Boolean,
    val records: List<Record>?,
)