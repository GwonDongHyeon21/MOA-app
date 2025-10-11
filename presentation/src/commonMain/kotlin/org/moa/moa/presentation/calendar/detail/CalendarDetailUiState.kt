package org.moa.moa.presentation.calendar.detail

import org.moa.moa.presentation.record.model.Record

data class CalendarDetailUiState(
    val date: String,
    val record: Record?,
)