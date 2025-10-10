package org.moa.moa.presentation.calendar.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moa.domain.repository.RecordRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.plus
import org.moa.moa.util.DummyData

class CalendarDetailViewModel(
    private val recordRepository: RecordRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        CalendarDetailUiState(
            date = null,
            record = null
        )
    )
    val uiState = _uiState.asStateFlow()

    fun loadRecord(date: String) {
        viewModelScope.launch {
            runCatching {
//                recordRepository.getRecord(date)
            }.onSuccess { record ->
                _uiState.value = _uiState.value.copy(
                    date = date,
                    record = DummyData.sampleRecords.find { it.date == date } // record
                )
            }.onFailure {

            }
        }
    }

    fun inputDate(date: String) {
        _uiState.value = _uiState.value.copy(date = date)
    }

    fun changeDay(datePeriod: Int) {
        _uiState.value.date?.let {
            val newDate = LocalDate.parse(it).plus(DatePeriod(days = datePeriod))
            loadRecord(it)
            _uiState.value = _uiState.value.copy(date = newDate.toString())
        }
    }
}