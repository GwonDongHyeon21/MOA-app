package org.moa.moa.presentation.record.textimage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moa.domain.usecase.RecordUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.moa.moa.presentation.UiState

class RecordViewModel(
    private val recordUseCase: RecordUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        RecordUiState(
            recordText = "",
            imageBytes = null,
            screenState = UiState.DEFAULT
        )
    )
    val uiState: StateFlow<RecordUiState> = _uiState

    fun changeRecordText(text: String) {
        _uiState.value = _uiState.value.copy(recordText = text)
    }

    fun changeImageBytes(imageBytes: ByteArray?) {
        _uiState.value = _uiState.value.copy(imageBytes = imageBytes)
    }

    fun addRecordData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(screenState = UiState.LOADING)
            runCatching {
//                recordUseCase.addRecordData()
            }.onSuccess {
                _uiState.value = _uiState.value.copy(screenState = UiState.SUCCESS)
            }.onFailure {
                _uiState.value = _uiState.value.copy(screenState = UiState.ERROR)
            }
        }
    }
}