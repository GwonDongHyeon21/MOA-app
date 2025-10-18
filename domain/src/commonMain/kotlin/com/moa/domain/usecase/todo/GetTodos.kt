package com.moa.domain.usecase.todo

import com.moa.domain.repository.TodoRepository

class GetTodos(
    private val todoRepository: TodoRepository,
) {
    suspend operator fun invoke() = todoRepository.getTodos()
}