package org.moa.moa.presentation.home.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.moa.moa.presentation.UiState
import org.moa.moa.repository.UiRecordRepositoryImpl

class HomeViewModel(
    private val repo: UiRecordRepositoryImpl,
) : ViewModel() {

    private val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date

    private val _uiState = MutableStateFlow(
        HomeUiState(
            screenState = UiState.LOADING,
            records = emptyList(),
            record = null
        )
    )
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repo.getRecords(today.toString())
            repo.records.collect { records ->
                _uiState.value = _uiState.value.copy(
                    screenState = UiState.SUCCESS,
                    records = records,
                    record = records.find { it.date == today.toString() }
                )
            }
        }
    }
}