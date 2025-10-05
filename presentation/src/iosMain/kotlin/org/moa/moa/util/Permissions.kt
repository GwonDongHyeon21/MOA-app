package org.moa.moa.util

import androidx.compose.runtime.Composable

@Composable
actual fun rememberPermissionState(): PermissionState {
    // TODO: AVAudioSession 권한 구현 (지금은 항상 Granted로 가정)
    return object : PermissionState {
        override val status: PermissionStatus = PermissionStatus.Granted
        override fun launchPermissionRequest() { /* no-op */
        }
    }
}