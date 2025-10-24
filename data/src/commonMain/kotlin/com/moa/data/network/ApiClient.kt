package com.moa.data.network

import com.moa.data.BuildConfig
import com.moa.domain.model.request.GetAccessTokenRequest
import com.moa.domain.model.response.GetAccessTokenResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.encodedPath
import io.ktor.http.takeFrom
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object ApiClient {

    private const val BASE_URL = BuildConfig.BASE_URL

    private val excludedExact = listOf(
        ApiConstants.GET_USER_INFO,
        ApiConstants.GOOGLE_SIGN_UP,
        ApiConstants.GET_ACCESS_TOKEN
    )

    private val refreshClient = HttpClient {
        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    println(message)
                }
            }
            level = LogLevel.ALL
        }

        defaultRequest {
            url.takeFrom(BASE_URL)
        }

        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
            })
        }
    }

    val httpClient = HttpClient {
        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    println(message)
                }
            }
            level = LogLevel.ALL
        }

        defaultRequest {
            url.takeFrom(BASE_URL)
        }

        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                prettyPrint = true
                isLenient = true
            })
        }

        install(Auth) {
            bearer {
                loadTokens {
                    val accessToken = TokenUtils.getAccessToken()
                    val refreshToken = TokenUtils.getRefreshToken()
                    if (!accessToken.isNullOrBlank() && !refreshToken.isNullOrBlank()) {
                        BearerTokens(
                            accessToken = accessToken,
                            refreshToken = refreshToken
                        )
                    } else {
                        null
                    }
                }

                sendWithoutRequest { request ->
                    val path = request.url.encodedPath
                    if (path in excludedExact) return@sendWithoutRequest false

                    true
                }

                refreshTokens {
                    val currentRefreshToken =
                        TokenUtils.getRefreshToken() ?: return@refreshTokens null
                    val resp = runCatching {
                        getAccessToken(GetAccessTokenRequest(currentRefreshToken))
                    }.getOrNull() ?: return@refreshTokens null

                    TokenUtils.saveAccessToken(resp.accessToken)
                    TokenUtils.saveRefreshToken(resp.refreshToken)

                    BearerTokens(
                        accessToken = resp.accessToken,
                        refreshToken = resp.refreshToken
                    )
                }
            }
        }
    }

    private suspend fun getAccessToken(token: GetAccessTokenRequest): GetAccessTokenResponse {
        return refreshClient.post(ApiConstants.GET_ACCESS_TOKEN) {
            contentType(ContentType.Application.Json)
            setBody(token)
        }.body()
    }
}