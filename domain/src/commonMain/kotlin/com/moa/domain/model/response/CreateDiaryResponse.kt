package com.moa.domain.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateDiaryResponse(
    @SerialName("message") val responseMessage: String,
    @SerialName("diary") val diary: Diary,
)