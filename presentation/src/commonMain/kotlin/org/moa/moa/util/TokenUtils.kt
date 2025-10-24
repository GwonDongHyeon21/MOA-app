package org.moa.moa.util

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.Settings
import com.russhwolf.settings.coroutines.toSuspendSettings

object TokenUtils {

    @OptIn(ExperimentalSettingsApi::class)
    suspend fun getAccessToken(): String? {
        val settings = Settings()
        val suspendSettings = settings.toSuspendSettings()
        return suspendSettings.getStringOrNull("moa_accessToken_token")
    }

    @OptIn(ExperimentalSettingsApi::class)
    suspend fun saveAccessToken(token: String): Boolean {
        return try {
            val settings = Settings()
            val suspendSettings = settings.toSuspendSettings()
            suspendSettings.putString("moa_accessToken_token", token)
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
            suspendSettings.putString("moa_refreshToken_token", token)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}