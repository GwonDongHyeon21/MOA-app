package org.moa.moa.presentation.record.recorder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moa.domain.model.request.AddRecordRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.moa.moa.presentation.record.recorder.model.RecordMode
import org.moa.moa.presentation.record.recorder.model.RecorderState
import org.moa.moa.presentation.record.recorder.platform.readFileAsBytes
import org.moa.moa.repository.UiRecordRepositoryImpl

class RecorderViewModel(
    private val uiRecordRepositoryImpl: UiRecordRepositoryImpl,
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        RecorderUiState(
            screenState = RecorderState.RECORD,
            recordMode = RecordMode.DEFAULT,
        )
    )
    val uiState = _uiState.asStateFlow()

    private val recordFilePath = MutableStateFlow<String?>(null)

    fun startRecord() {
        _uiState.value = _uiState.value.copy(recordMode = RecordMode.START)
    }

    fun pauseRecord() {
        _uiState.value = _uiState.value.copy(recordMode = RecordMode.PAUSE)
    }

    fun stopRecord(recordPath: String) {
        recordFilePath.value = recordPath
        _uiState.value = _uiState.value.copy(screenState = RecorderState.PLAYING(recordPath))
    }

    fun resetRecord() {
        _uiState.value = _uiState.value.copy(
            recordMode = RecordMode.DEFAULT,
            screenState = RecorderState.RECORD
        )
    }

    fun addRecord() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(screenState = RecorderState.LOADING)

            val filePath = recordFilePath.value
            if (filePath == null) {
                _uiState.value = _uiState.value.copy(screenState = RecorderState.ERROR)
                return@launch
            }

            val audioBytes = readFileAsBytes(filePath)
            if (audioBytes == null) {
                _uiState.value = _uiState.value.copy(screenState = RecorderState.ERROR)
                return@launch
            }

            runCatching {
                uiRecordRepositoryImpl.addRecord(
                    AddRecordRequest(
                        content = null,
                        imageBytes = null,
                        audioBytes = audioBytes,

                        )
                )
            }.onSuccess {
                _uiState.value = _uiState.value.copy(screenState = RecorderState.SUCCESS)
            }.onFailure {
                _uiState.value = _uiState.value.copy(screenState = RecorderState.ERROR)
            }
        }
    }
}