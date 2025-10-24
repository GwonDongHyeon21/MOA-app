package org.moa.moa.presentation.home.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.moa.moa.repository.UiRecordRepositoryImpl

class HomeDetailViewModel(
    private val repo: UiRecordRepositoryImpl,
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        HomeDetailUiState(
            screenState = HomeDetailScreenState.SUCCESS,
            records = emptyList(),
            record = null
        )
    )
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repo.todayRecords.collect { records ->
                _uiState.value = _uiState.value.copy(records = records)
            }
        }
    }

    fun loadRecord(recordNumber: Int) {
        _uiState.value = _uiState.value.copy(record = _uiState.value.records[recordNumber])
    }

    fun deleteRecord() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(screenState = HomeDetailScreenState.LOADING)
            runCatching {
                // 조각 삭제
                delay(1_000)
            }.onSuccess {
                _uiState.value =
                    _uiState.value.copy(screenState = HomeDetailScreenState.DELETE_SUCCESS)
            }.onFailure {
                _uiState.value = _uiState.value.copy(screenState = HomeDetailScreenState.ERROR)
            }
        }
    }
}