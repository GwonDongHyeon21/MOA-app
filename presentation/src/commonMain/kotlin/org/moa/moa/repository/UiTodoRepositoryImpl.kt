package org.moa.moa.repository

import com.moa.domain.DummyData
import com.moa.domain.model.request.AddTodoRequest
import com.moa.domain.model.request.DeleteTodoRequest
import com.moa.domain.model.response.TodoItemResponse
import com.moa.domain.usecase.todo.TodoUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class UiTodoRepositoryImpl(
    private val todoUseCase: TodoUseCase,
) {

    private val _todos = MutableStateFlow<List<TodoItemResponse>>(DummyData.todoExamples)
    val todos = _todos.asStateFlow()

    suspend fun getTodos() {
        runCatching {
            todoUseCase.getTodos.invoke()
        }.onSuccess { todos ->
            _todos.value = todos.todos
        }.onFailure {
            throw it
        }
    }

    suspend fun addTodo(todo: AddTodoRequest) {
        runCatching {
            todoUseCase.addTodo.invoke(todo)
        }.onSuccess {
            getTodos()
        }.onFailure {
            throw it
        }
    }

    suspend fun deleteTodo(todo: DeleteTodoRequest) {
        runCatching {
            todoUseCase.deleteTodo.invoke(todo)
        }.onSuccess {
            getTodos()
        }.onFailure {
            throw it
        }
    }
}