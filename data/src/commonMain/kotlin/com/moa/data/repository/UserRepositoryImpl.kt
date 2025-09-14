package com.moa.data.repository

import com.moa.domain.model.User
import com.moa.domain.repository.UserRepository

class UserRepositoryImpl : UserRepository {
    override suspend fun getUser(): User {
        return User(id = "user123", name = "KMP User (from Data Layer)")
    }
}