package org.moa.moa.presentation.todo

import com.moa.domain.model.response.TodoItemResponse
import kotlinx.datetime.LocalDate

data class TodoUiState(
    val todos: List<TodoItemResponse>,
    val content: String,
    val date: LocalDate,
    val dateTodos: List<TodoItemResponse>,
    val monthDates: List<LocalDate>,
    val yearMonths: List<LocalDate>,
    val monthDropDownExpanded: Boolean,
    val screenState: TodoScreenState,
)

enum class TodoScreenState {
    TODO,
    ERROR
}