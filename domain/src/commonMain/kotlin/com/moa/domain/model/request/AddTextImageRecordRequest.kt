package com.moa.domain.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddTextImageRecordRequest(
    @SerialName("context") val content: String,
    @SerialName("image") val imageBytes: ByteArray?,
)