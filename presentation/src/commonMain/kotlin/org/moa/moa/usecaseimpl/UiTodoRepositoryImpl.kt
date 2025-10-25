package org.moa.moa.usecaseimpl

import com.moa.domain.model.request.AddTodoRequest
import com.moa.domain.model.request.DeleteTodoRequest
import com.moa.domain.model.request.UpdateTodoRequest
import com.moa.domain.model.response.TodoItemResponse
import com.moa.domain.usecase.todo.TodoUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class UiTodoRepositoryImpl(
    private val todoUseCase: TodoUseCase,
) {

    private val _todos = MutableStateFlow<List<TodoItemResponse>>(emptyList())
    val todos = _todos.asStateFlow()

    suspend fun getTodos() {
        runCatching {
            todoUseCase.getTodos()
        }.onSuccess { todos ->
            _todos.value = todos.todos
        }.onFailure {
            throw it
        }
    }

    suspend fun addTodo(todo: AddTodoRequest) {
        runCatching {
            todoUseCase.addTodo(todo)
        }.onSuccess {
            getTodos()
        }.onFailure {
            throw it
        }
    }

    suspend fun updateTodo(todo: UpdateTodoRequest) {
        fun updateDone(done: Boolean) {
            _todos.value = _todos.value.map {
                if (it.id == todo.id) it.copy(done = done) else it
            }
        }

        updateDone(todo.done)
        runCatching {
            todoUseCase.updateTodo(todo)
        }.onFailure {
            updateDone(!todo.done)
            throw it
        }
    }

    suspend fun deleteTodo(todo: DeleteTodoRequest) {
        val previous = _todos.value
        _todos.value = previous.filterNot { it.id == todo.id }

        runCatching {
            todoUseCase.deleteTodo(todo)
        }.onFailure { e ->
            _todos.value = previous
            throw e
        }
    }
}