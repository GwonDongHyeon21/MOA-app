package com.moa.domain.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TodosResponse(
    @SerialName("message") val responseMessage: String,
    @SerialName("count") val todosCount: Int,
    @SerialName("todos") val todos: List<TodoItemResponse>,
)

@Serializable
data class TodoItemResponse(
    @SerialName("id") val id: String,
    @SerialName("context") val content: String,
    @SerialName("date") val date: String,
    @SerialName("done") val done: Boolean,
    @SerialName("createdAt") val createdAt: String,
    @SerialName("updatedAt") val updatedAt: String,
)
