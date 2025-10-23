package com.moa.domain.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetAccessTokenRequest(
    @SerialName("refreshToken") val token: String,
)