package com.moa.domain.usecase.sign

import com.moa.domain.model.request.UserRequest
import com.moa.domain.repository.SignRepository

class GoogleSignUp(
    private val signRepository: SignRepository,
) {
    suspend operator fun invoke(user: UserRequest) {
        signRepository.googleSignUp(user)
    }
}