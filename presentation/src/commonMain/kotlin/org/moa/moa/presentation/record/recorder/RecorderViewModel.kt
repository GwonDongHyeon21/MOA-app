package org.moa.moa.presentation.record.recorder

import androidx.lifecycle.ViewModel
import com.moa.domain.usecase.RecordUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.moa.moa.presentation.record.recorder.model.RecordMode
import org.moa.moa.presentation.record.recorder.model.RecorderState

class RecorderViewModel(
    private val recordUseCase: RecordUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        RecorderUiState(
            screenState = RecorderState.RECORD,
            recordMode = RecordMode.DEFAULT,
        )
    )
    val uiState = _uiState.asStateFlow()

    fun startRecord() {
        _uiState.value = _uiState.value.copy(recordMode = RecordMode.START)
    }

    fun pauseRecord() {
        _uiState.value = _uiState.value.copy(recordMode = RecordMode.PAUSE)
    }

    fun stopRecord(recordPath: String) {
        _uiState.value = _uiState.value.copy(
            screenState = RecorderState.PLAYING(recordPath),
        )
    }
}