package org.moa.moa.util

import androidx.compose.runtime.Composable

interface CameraController {
    suspend fun takePicture(): ByteArray?
}

@Composable
expect fun rememberCameraController(): CameraController