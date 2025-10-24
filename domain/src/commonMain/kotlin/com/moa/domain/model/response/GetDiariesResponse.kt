package com.moa.domain.model.response

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetDiariesResponse(
    @SerialName("count") val count: Int,
    @SerialName("diaries") val diaries: List<Diary>,
)

@Serializable
data class Diary(
    @SerialName("id") val id: String,
    @SerialName("text") val content: String,
    @SerialName("persona") val persona: Int,
    @SerialName("date") val date: String,
    @SerialName("createdAt") val createdAt: String,
    @SerialName("images") val images: List<String> = emptyList(),
    @SerialName("emotion") val emotion: String? = null,
) {
    val dataToLocalDateTime =
        Instant.parse(createdAt).toLocalDateTime(TimeZone.currentSystemDefault())
}