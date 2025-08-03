package com.moa.domain.usecase

import com.moa.domain.model.User
import com.moa.domain.repository.UserRepository

class GetUserUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(): User = userRepository.getUser()
}