package org.moa.moa.presentation.record.textimage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moa.domain.model.request.RecordRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.moa.moa.presentation.UiState
import org.moa.moa.repository.UiRecordRepositoryImpl

class RecordViewModel(
    private val repo: UiRecordRepositoryImpl,
) : ViewModel() {

    private val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date

    private val _uiState = MutableStateFlow(
        RecordUiState(
            date = today.toString(),
            content = "",
            imageBytes = null,
            screenState = UiState.DEFAULT
        )
    )
    val uiState: StateFlow<RecordUiState> = _uiState

    fun changeRecordText(text: String) {
        _uiState.value = _uiState.value.copy(content = text)
    }

    fun changeImageBytes(imageBytes: ByteArray?) {
        _uiState.value = _uiState.value.copy(imageBytes = imageBytes)
    }

    fun addRecord() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(screenState = UiState.LOADING)
            runCatching {
                repo.addRecord(
                    RecordRequest(
                        date = _uiState.value.date,
                        content = _uiState.value.content,
                        imageBytes = _uiState.value.imageBytes,
                    )
                )
            }.onSuccess {
                _uiState.value = _uiState.value.copy(screenState = UiState.SUCCESS)
            }.onFailure {
                _uiState.value = _uiState.value.copy(screenState = UiState.ERROR)
            }
        }
    }
}