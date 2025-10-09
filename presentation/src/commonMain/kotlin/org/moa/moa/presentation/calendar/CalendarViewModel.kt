package org.moa.moa.presentation.calendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moa.domain.usecase.RecordUseCase
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
import org.moa.moa.util.DummyData

class CalendarViewModel(
    private val recordUseCase: RecordUseCase,
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
        loadRecords()
    }

    private fun loadRecords() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(screenState = UiState.LOADING)
            runCatching {
//                recordUseCase.getRecordsByMonth()
            }.onSuccess { records ->
                _uiState.value = _uiState.value.copy(
                    screenState = UiState.SUCCESS,
                    recordsByMonth = DummyData.sampleRecords // records
                )
            }.onFailure {
                _uiState.value = _uiState.value.copy(screenState = UiState.ERROR)
            }
        }
    }

    fun changeYearMonth(datePeriod: Int) {
        val (newYear, newMonth) = LocalDate(
            _uiState.value.year,
            _uiState.value.month,
            1
        ).plus(DatePeriod(months = datePeriod)).run { year to month }
        _uiState.value = _uiState.value.copy(year = newYear, month = newMonth)
    }
}