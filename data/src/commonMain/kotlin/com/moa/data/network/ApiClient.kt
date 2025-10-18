package com.moa.data.network

import com.moa.data.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.takeFrom
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object ApiClient {

    private const val BASE_URL = BuildConfig.BASE_URL

    val httpClient = HttpClient {
        install(Logging) {
            logger = Logger.DEFAULT     // 기본 로거 사용
            level = LogLevel.ALL        // 요청/응답 전체 로그
        }

        expectSuccess = true

        defaultRequest {
            url.takeFrom(BASE_URL)
        }

        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true // 알 수 없는 키 무시
                prettyPrint = true // JSON 예쁘게 출력 (디버깅용)
                isLenient = true // 느슨한 파싱 허용
            })
        }
    }
}