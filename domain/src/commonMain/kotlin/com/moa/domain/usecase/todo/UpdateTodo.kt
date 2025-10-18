package com.moa.domain.usecase.todo

import com.moa.domain.model.request.UpdateTodoRequest
import com.moa.domain.repository.TodoRepository

class UpdateTodo(
    private val todoRepository: TodoRepository,
) {
    suspend operator fun invoke(todo: UpdateTodoRequest) = todoRepository.updateTodo(todo)
}