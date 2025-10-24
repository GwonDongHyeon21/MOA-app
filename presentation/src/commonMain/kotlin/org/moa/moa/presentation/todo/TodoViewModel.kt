package org.moa.moa.presentation.todo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moa.domain.model.request.AddTodoRequest
import com.moa.domain.model.request.DeleteTodoRequest
import com.moa.domain.model.request.UpdateTodoRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import org.moa.moa.usecaseimpl.UiTodoRepositoryImpl

class TodoViewModel(
    private val uiTodoRepositoryImpl: UiTodoRepositoryImpl,
) : ViewModel() {

    private val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date

    private val _uiState = MutableStateFlow(
        TodoUiState(
            todos = emptyList(),
            content = "",
            date = today,
            dateTodos = emptyList(),
            monthDates = getMonthDates(today),
            yearMonths = generateYearMonthsList(),
            screenState = TodoScreenState.TODO
        )
    )
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            uiTodoRepositoryImpl.todos.collect { todos ->
                _uiState.value = _uiState.value.copy(
                    todos = todos,
                    dateTodos = todos.filter { it.date == _uiState.value.date.toString() }
                )
            }
        }
    }

    private fun generateYearMonthsList(yearCount: Int = 10): List<LocalDate> =
        (today.year - yearCount until today.year + yearCount)
            .flatMap { year -> (1..12).map { month -> LocalDate(year, month, 1) } }

    fun changeContent(content: String) {
        _uiState.value = _uiState.value.copy(content = content)
    }

    fun changeDate(date: LocalDate) {
        if (_uiState.value.date != date) {
            _uiState.value = _uiState.value.copy(
                date = date,
                dateTodos = _uiState.value.todos.filter { it.date == date.toString() },
                monthDates = getMonthDates(date)
            )
        }
    }

    private fun getMonthDates(date: LocalDate): List<LocalDate> {
        val firstDayOfMonth = LocalDate(date.year, date.monthNumber, 1)
        val nextMonth = if (date.monthNumber == 12)
            LocalDate(date.year + 1, 1, 1)
        else
            LocalDate(date.year, date.monthNumber + 1, 1)
        val lastDayOfMonth = nextMonth.minus(DatePeriod(days = 1))

        return List(lastDayOfMonth.dayOfMonth) { index -> firstDayOfMonth.plus(DatePeriod(days = index)) }
    }

    fun addTodo() {
        if (_uiState.value.content.isNotBlank()) {
            viewModelScope.launch {
                runCatching {
                    uiTodoRepositoryImpl.addTodo(
                        AddTodoRequest(
                            content = _uiState.value.content,
                            date = _uiState.value.date.toString()
                        )
                    )
                }.onSuccess {
                    _uiState.value = _uiState.value.copy(content = "")
                }.onFailure {
                    _uiState.value = _uiState.value.copy(screenState = TodoScreenState.ERROR)
                }
            }
        }
    }

    fun changeDone(id: String, done: Boolean) {
        viewModelScope.launch {
            uiTodoRepositoryImpl.updateTodo(
                UpdateTodoRequest(id = id, done = !done)
            )
        }
    }

    fun deleteTodo(id: String) {
        viewModelScope.launch {
            runCatching {
                uiTodoRepositoryImpl.deleteTodo(
                    DeleteTodoRequest(id = id)
                )
            }.onFailure {
                _uiState.value = _uiState.value.copy(screenState = TodoScreenState.ERROR)
            }
        }
    }
}