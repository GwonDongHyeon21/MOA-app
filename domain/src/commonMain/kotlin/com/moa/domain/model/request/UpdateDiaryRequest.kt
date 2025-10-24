package com.moa.domain.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateDiaryRequest(
    @SerialName("id") val id: String,
    @SerialName("text") val content: String,
    @SerialName("emotion") val emotion: String?,
)