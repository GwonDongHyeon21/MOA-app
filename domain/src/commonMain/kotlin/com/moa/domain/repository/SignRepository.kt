package com.moa.domain.repository

import com.moa.domain.model.request.UserRequest
import com.moa.domain.model.response.UserResponse

interface SignRepository {
    suspend fun getUserInfo(token: String): UserResponse
    suspend fun googleSignUp(user: UserRequest): String
}