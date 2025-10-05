package org.moa.moa.presentation.record.textimage.platform

import androidx.compose.runtime.Composable

interface CameraController {
    suspend fun takePicture(): ByteArray?
}

@Composable
expect fun rememberCameraController(): CameraController