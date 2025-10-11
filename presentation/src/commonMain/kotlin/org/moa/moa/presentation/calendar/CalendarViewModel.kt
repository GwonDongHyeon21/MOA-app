package org.moa.moa.presentation.calendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import org.moa.moa.presentation.UiState
import org.moa.moa.repository.UiRecordRepositoryImpl

class CalendarViewModel(
    private val repo: UiRecordRepositoryImpl,
) : ViewModel() {

    private val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date

    private val _uiState = MutableStateFlow(
        CalendarUiState(
            screenState = UiState.LOADING,
            today = today,
            year = today.year,
            month = today.month,
            startOn = DayOfWeek.SUNDAY,
            recordsByMonth = emptyList()
        )
    )
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getRecords(today.toString())

            repo.records.collect { records ->
                _uiState.value = _uiState.value.copy(recordsByMonth = records)
            }
        }
    }

    private fun getRecords(date: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(screenState = UiState.LOADING)
            runCatching {
                repo.getRecords(date)
            }.onSuccess {
                _uiState.value = _uiState.value.copy(screenState = UiState.SUCCESS)
            }.onFailure {
                _uiState.value = _uiState.value.copy(screenState = UiState.ERROR)
            }
        }
    }

    fun changeYearMonth(datePeriod: Int) {
        val newDate = LocalDate(
            _uiState.value.year,
            _uiState.value.month,
            1
        ).plus(DatePeriod(months = datePeriod)).run { year to month }
        val (newYear, newMonth) = newDate.first to newDate.second

        if (_uiState.value.year != newYear) getRecords(newDate.toString())

        _uiState.value = _uiState.value.copy(year = newYear, month = newMonth)
    }
}