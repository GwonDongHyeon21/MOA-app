package org.moa.moa.presentation.home.home

import org.moa.moa.presentation.UiState
import org.moa.moa.presentation.record.model.Record

data class HomeUiState(
    val screenState: UiState,
    val records: List<Record>,
    val record: Record?,
)