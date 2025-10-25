package org.moa.moa.presentation.util

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.Settings
import com.russhwolf.settings.coroutines.toSuspendSettings

object TokenUtils {

    private const val ACCESS_TOKEN = "moa_accessToken_token"
    private const val REFRESH_TOKEN = "moa_refreshToken_token"
    private const val PERSONA = "moa_persona"

    @OptIn(ExperimentalSettingsApi::class)
    suspend fun getAccessToken(): String? {
        val settings = Settings()
        val suspendSettings = settings.toSuspendSettings()
        return suspendSettings.getStringOrNull(ACCESS_TOKEN)
    }

    @OptIn(ExperimentalSettingsApi::class)
    suspend fun saveAccessToken(token: String): Boolean {
        return try {
            val settings = Settings()
            val suspendSettings = settings.toSuspendSettings()
            suspendSettings.putString(ACCESS_TOKEN, token)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    @OptIn(ExperimentalSettingsApi::class)
    suspend fun saveRefreshToken(token: String): Boolean {
        return try {
            val settings = Settings()
            val suspendSettings = settings.toSuspendSettings()
            suspendSettings.putString(REFRESH_TOKEN, token)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    @OptIn(ExperimentalSettingsApi::class)
    suspend fun savePersona(persona: Int): Boolean {
        return try {
            val settings = Settings()
            val suspendSettings = settings.toSuspendSettings()
            suspendSettings.putInt(PERSONA, persona)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    @OptIn(ExperimentalSettingsApi::class)
    suspend fun getPersona(): Int? {
        val settings = Settings()
        val suspendSettings = settings.toSuspendSettings()
        return suspendSettings.getIntOrNull(PERSONA)
    }
}