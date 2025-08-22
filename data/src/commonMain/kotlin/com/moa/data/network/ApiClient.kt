package com.moa.data.network

import com.moa.data.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.Url
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object ApiClient {
    private const val BASE_URL = BuildConfig.BASE_URL

    val httpClient = HttpClient {

        defaultRequest {
            val url = Url(BASE_URL)
            host = url.host
            port = url.port
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