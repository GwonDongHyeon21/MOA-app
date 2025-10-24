package com.moa.domain.model.response

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
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
    @SerialName("type") val type: String,
    @SerialName("context") val content: String,
    @SerialName("imageUrl") val imageUrl: String?,
    @SerialName("createdAt") val date: String,
    @SerialName("updatedAt") val updatedAt: String? = null,
) {
    val dataToLocalDateTime = Instant.parse(date).toLocalDateTime(TimeZone.currentSystemDefault())
}