package com.moa.domain.repository

import com.moa.domain.model.ResponseMessage
import com.moa.domain.model.User

interface SignRepository {
    suspend fun signUp(user: User): ResponseMessage
}