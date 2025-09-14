package com.moa.domain.usecase

import com.moa.domain.model.User
import com.moa.domain.repository.SignRepository

class SignUseCase(private val signRepository: SignRepository) {
    suspend fun signUp(user: User) {
        signRepository.signUp(user)
    }
}
