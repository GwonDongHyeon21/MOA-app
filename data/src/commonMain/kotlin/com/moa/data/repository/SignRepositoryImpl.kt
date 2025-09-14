package com.moa.data.repository

import com.moa.data.network.ApiService
import com.moa.domain.model.ResponseMessage
import com.moa.domain.model.User
import com.moa.domain.repository.SignRepository

class SignRepositoryImpl(
    private val apiService: ApiService,
) : SignRepository {
    override suspend fun signUp(user: User): ResponseMessage {
        val response = apiService.signUp(user)
        return response
    }
}