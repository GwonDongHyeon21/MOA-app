package com.moa.domain.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserRequest(
    @SerialName("providerID") val userId: String,
    @SerialName("email") val email: String,
    @SerialName("name") val name: String,
    @SerialName("picture") val pictureUrl: String,
    @SerialName("nickname") val id: String,
    @SerialName("birthdate") val birthdate: String,
    @SerialName("gender") val gender: String,
)