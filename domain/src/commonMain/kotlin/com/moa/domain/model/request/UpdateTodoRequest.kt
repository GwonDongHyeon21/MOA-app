package com.moa.domain.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateTodoRequest(
    @SerialName("_id") val id: String,
    @SerialName("done") val done: Boolean,
)