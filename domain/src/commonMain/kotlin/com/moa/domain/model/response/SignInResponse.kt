package com.moa.domain.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignInResponse(
    @SerialName("status") val status: String,

    // SignIn 성공
    @SerialName("accessToken") val accessToken: String,
    @SerialName("refreshToken") val refreshToken: String,
    @SerialName("user") val user: UserInfo? = null,

    // SignUp 필요
    @SerialName("prefill") val prefill: PrefillInfo? = null,
    @SerialName("hint") val hint: HintInfo? = null,
)

@Serializable
data class UserInfo(
    @SerialName("id") val id: String,
    @SerialName("email") val email: String,
    @SerialName("name") val name: String,
    @SerialName("picture") val picture: String,
    @SerialName("nickname") val nickname: String,
)

@Serializable
data class PrefillInfo(
    @SerialName("email") val email: String,
    @SerialName("name") val name: String,
    @SerialName("picture") val picture: String,
)

@Serializable
data class HintInfo(
    @SerialName("provider") val provider: String,
    @SerialName("providerID") val providerId: String,
)