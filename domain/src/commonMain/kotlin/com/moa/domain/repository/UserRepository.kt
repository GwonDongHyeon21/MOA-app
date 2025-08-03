package com.moa.domain.repository

import com.moa.domain.model.User

interface UserRepository {
    suspend fun getUser(): User
}