package com.moa.data.repositoryimpl

import com.moa.data.network.ApiService
import com.moa.domain.model.request.AddTodoRequest
import com.moa.domain.model.request.DeleteTodoRequest
import com.moa.domain.model.request.UpdateTodoRequest
import com.moa.domain.model.response.ResponseMessage
import com.moa.domain.model.response.TodosResponse
import com.moa.domain.repository.TodoRepository

class TodoRepositoryImpl(
    private val apiService: ApiService,
) : TodoRepository {

    override suspend fun getTodos(): TodosResponse {
        return apiService.getTodos()
    }

    override suspend fun addTodo(todo: AddTodoRequest): ResponseMessage {
        return apiService.addTodo(todo)
    }

    override suspend fun updateTodo(todo: UpdateTodoRequest): ResponseMessage {
        return apiService.updateTodo(todo)
    }

    override suspend fun deleteTodo(todo: DeleteTodoRequest): ResponseMessage {
        return apiService.deleteTodo(todo)
    }
}