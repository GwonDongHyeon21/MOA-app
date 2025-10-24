package com.moa.domain.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateDiaryRequest(
    @SerialName("date") val date: String,
    @SerialName("persona") val persona: Int,
)