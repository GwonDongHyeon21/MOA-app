package com.moa.domain.usecase.sign

import com.moa.domain.model.request.GetUserInfoRequest
import com.moa.domain.model.response.SignInResponse
import com.moa.domain.repository.SignRepository

class GetUserInfo(
    private val signRepository: SignRepository,
) {
    suspend operator fun invoke(token: GetUserInfoRequest): SignInResponse {
        return signRepository.getUserInfo(token)
    }
}