package com.moa.domain.usecase.todo

import com.moa.domain.model.request.DeleteTodoRequest
import com.moa.domain.repository.TodoRepository

class DeleteTodo(
    private val todoRepository: TodoRepository,
) {
    suspend operator fun invoke(todo: DeleteTodoRequest) = todoRepository.deleteTodo(todo)
}