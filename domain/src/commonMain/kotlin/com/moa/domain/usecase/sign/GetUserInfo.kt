package com.moa.domain.usecase.sign

import com.moa.domain.repository.SignRepository

class GetUserInfo(
    private val signRepository: SignRepository,
) {
    suspend operator fun invoke(token: String){
        signRepository.getUserInfo(token)
    }
}