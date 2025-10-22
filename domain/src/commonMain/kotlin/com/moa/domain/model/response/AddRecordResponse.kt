package com.moa.domain.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddRecordResponse(
    @SerialName("message") val responseMessage: String,
    @SerialName("record") val record: RecordResponse,
)

@Serializable
data class RecordResponse(
    @SerialName("id") val id: String,
    @SerialName("type") val type: String,
    @SerialName("context") val content: String,
    @SerialName("imageUrl") val imageUrl: String? = null,
    @SerialName("createdAt") val createdAt: String,
)
