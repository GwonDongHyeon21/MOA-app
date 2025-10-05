package org.moa.moa.util

import androidx.compose.runtime.Composable

enum class PermissionStatus { Granted, Denied, ShouldShowRationale }

interface PermissionState {
    val status: PermissionStatus
    fun launchPermissionRequest()
}

@Composable
expect fun rememberPermissionState(): PermissionState