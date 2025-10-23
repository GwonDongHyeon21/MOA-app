package org.moa.moa.presentation.sign.platform

expect class GoogleAuthCodeProvider {
    suspend fun fetchServerAuthCode(): String
}