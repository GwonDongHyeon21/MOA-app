package com.moa.data.repositoryimpl

import com.moa.data.network.ApiService
import com.moa.domain.model.request.UserRequest
import com.moa.domain.model.response.UserResponse
import com.moa.domain.repository.SignRepository

class SignRepositoryImpl(
    private val apiService: ApiService,
) : SignRepository {

    override suspend fun getUserInfo(token: String): UserResponse {
        return apiService.getUserInfo(token)
    }

    override suspend fun googleSignUp(user: UserRequest): String {
        return apiService.googleSignUp(user)
    }
}