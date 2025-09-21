package org.moa.moa.presentation.record

import org.moa.moa.presentation.UiState

data class RecordUiState(
    val recordText: String,
    val imageBytes: ByteArray?,
    val screenState: UiState,
)