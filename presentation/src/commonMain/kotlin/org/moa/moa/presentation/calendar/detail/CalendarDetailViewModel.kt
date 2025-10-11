package org.moa.moa.presentation.calendar.detail

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.plus
import org.moa.moa.repository.UiRecordRepositoryImpl

class CalendarDetailViewModel(
    private val repo: UiRecordRepositoryImpl,
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        CalendarDetailUiState(
            date = "",
            record = null
        )
    )
    val uiState = _uiState.asStateFlow()

    fun findRecord(date: String) {
        _uiState.value =
            _uiState.value.copy(date = date, record = repo.records.value.find { it.date == date })
    }

    fun changeDay(datePeriod: Int) {
        val newDate =
            LocalDate.parse(_uiState.value.date).plus(DatePeriod(days = datePeriod)).toString()
        findRecord(newDate)
    }
}