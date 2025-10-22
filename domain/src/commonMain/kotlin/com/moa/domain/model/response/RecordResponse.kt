package com.moa.domain.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetRecordsResponse(
    @SerialName("message") val responseMessage: String,
    @SerialName("count") val recordsCount: Int,
    @SerialName("records") val records: List<RecordItem>,
)

@Serializable
data class RecordItem(
    @SerialName("id") val id: String,
    @SerialName("type") val type: String,  // "text", "image", "text+image", "audio"
    @SerialName("context") val content: String,
    @SerialName("imageUrl") val imageUrl: String?,
    @SerialName("createdAt") val date: String,
    @SerialName("updatedAt") val updatedAt: String,
)