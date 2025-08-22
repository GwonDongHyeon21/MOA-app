package com.moa.data.network

import com.moa.domain.model.ResponseMessage
import com.moa.domain.model.User
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

interface ApiService {

    suspend fun signUp(user: User): ResponseMessage {
        return ApiClient.httpClient.post("") { // 백엔드 api 개발 후에 실제 url로 수정할 예정
            contentType(ContentType.Application.Json)
            setBody(user)
        }.body()
    }
}