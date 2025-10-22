package com.moa.domain.usecase.todo

import com.moa.domain.model.request.AddTodoRequest
import com.moa.domain.repository.TodoRepository

class AddTodo(
    private val todoRepository: TodoRepository,
) {
    suspend operator fun invoke(todo: AddTodoRequest) = todoRepository.addTodo(todo)
}