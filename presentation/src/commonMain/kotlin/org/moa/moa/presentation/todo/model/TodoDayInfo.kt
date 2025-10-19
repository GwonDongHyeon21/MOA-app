package org.moa.moa.presentation.todo.model

import com.moa.domain.model.response.TodoItemResponse
import kotlinx.datetime.LocalDate

data class TodoDayInfo(
    val date: LocalDate,
    val isToday: Boolean,
    val isCurrentMonth: Boolean,
    val todos: List<TodoItemResponse>?,
)
