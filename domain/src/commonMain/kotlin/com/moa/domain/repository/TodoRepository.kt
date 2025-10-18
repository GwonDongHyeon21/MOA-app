package com.moa.domain.repository

import com.moa.domain.model.request.AddTodoRequest
import com.moa.domain.model.request.DeleteTodoRequest
import com.moa.domain.model.request.UpdateTodoRequest
import com.moa.domain.model.response.ResponseMessage
import com.moa.domain.model.response.TodosResponse

interface TodoRepository {
    suspend fun getTodos(): TodosResponse
    suspend fun addTodo(todo: AddTodoRequest): ResponseMessage
    suspend fun updateTodo(todo: UpdateTodoRequest): ResponseMessage
    suspend fun deleteTodo(todo: DeleteTodoRequest): ResponseMessage
}