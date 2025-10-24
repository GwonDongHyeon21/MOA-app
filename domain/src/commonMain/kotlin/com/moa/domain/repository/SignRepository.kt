package com.moa.domain.repository

import com.moa.domain.model.request.GetUserInfoRequest
import com.moa.domain.model.request.UserRequest
import com.moa.domain.model.response.SignInResponse

interface SignRepository {
    suspend fun getUserInfo(token: GetUserInfoRequest): SignInResponse
    suspend fun googleSignUp(user: UserRequest): SignInResponse
}