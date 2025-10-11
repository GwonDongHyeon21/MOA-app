package org.moa.moa.presentation.calendar.detail

import org.moa.moa.presentation.UiState
import org.moa.moa.presentation.record.model.Record

data class CalendarDetailUiState(
    val screenState: UiState,
    val date: String,
    val record: Record?,
    val records: List<Record>,
)