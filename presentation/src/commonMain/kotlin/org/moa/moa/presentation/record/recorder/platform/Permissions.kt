package org.moa.moa.presentation.record.recorder.platform

import androidx.compose.runtime.Composable

enum class PermissionStatus { Granted, Denied, ShouldShowRationale }

interface PermissionState {
    val status: PermissionStatus
    fun launchPermissionRequest()
}

@Composable
expect fun rememberPermissionState(): PermissionState