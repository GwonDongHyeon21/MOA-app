package org.moa.moa.presentation.record.recorder

import org.moa.moa.presentation.record.recorder.model.RecordMode
import org.moa.moa.presentation.record.recorder.model.RecorderState

data class RecorderUiState(
    val screenState: RecorderState,
    val recordMode: RecordMode,
)