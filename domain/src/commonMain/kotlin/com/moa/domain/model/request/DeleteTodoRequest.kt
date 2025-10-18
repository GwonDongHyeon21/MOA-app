package com.moa.domain.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DeleteTodoRequest(
    @SerialName("_id") val id: String,
)