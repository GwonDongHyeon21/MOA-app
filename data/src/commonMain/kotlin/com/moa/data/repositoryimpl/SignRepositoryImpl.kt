package com.moa.data.repositoryimpl

import com.moa.data.network.ApiService
import com.moa.domain.model.request.GetUserInfoRequest
import com.moa.domain.model.request.UserRequest
import com.moa.domain.model.response.SignInResponse
import com.moa.domain.repository.SignRepository

class SignRepositoryImpl(
    private val apiService: ApiService,
) : SignRepository {

    override suspend fun getUserInfo(token: GetUserInfoRequest): SignInResponse {
        return apiService.getUserInfo(token)
    }

    override suspend fun googleSignUp(user: UserRequest): SignInResponse {
        return apiService.googleSignUp(user)
    }
}