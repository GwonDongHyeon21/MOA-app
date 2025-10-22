package com.moa.domain.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddTodoRequest(
    @SerialName("context") val content: String,
    @SerialName("date") val date: String,
)