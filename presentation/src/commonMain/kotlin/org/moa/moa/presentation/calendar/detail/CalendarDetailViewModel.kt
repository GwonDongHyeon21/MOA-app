package org.moa.moa.presentation.calendar.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.plus
import org.moa.moa.presentation.UiState
import org.moa.moa.repository.UiRecordRepositoryImpl

class CalendarDetailViewModel(
    private val repo: UiRecordRepositoryImpl,
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        CalendarDetailUiState(
            screenState = UiState.LOADING,
            date = "",
            diary = null,
            diaries = emptyList()
        )
    )
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repo.diaries.collect { diaries ->
                _uiState.value = _uiState.value.copy(
                    diaries = diaries,
                    screenState = UiState.SUCCESS
                )
            }
        }
    }

    fun findRecord(date: String) {
        _uiState.value = _uiState.value.copy(
            date = date,
            diary = _uiState.value.diaries.find { it.date == date }
        )
    }

    fun changeDay(datePeriod: Int) {
        val date = LocalDate.parse(_uiState.value.date)
        val newDate = date.plus(DatePeriod(days = datePeriod))

        if (date.year != newDate.year) getRecords(newDate.toString())

        findRecord(newDate.toString())
    }

    private fun getRecords(date: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(screenState = UiState.LOADING)
            runCatching {
                repo.getDiaries()
            }.onSuccess {
                _uiState.value = _uiState.value.copy(screenState = UiState.SUCCESS)
            }.onFailure {
                _uiState.value = _uiState.value.copy(screenState = UiState.ERROR)
            }
        }
    }
}