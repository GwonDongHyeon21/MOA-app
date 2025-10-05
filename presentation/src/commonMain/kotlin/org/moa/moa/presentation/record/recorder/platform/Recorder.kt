package org.moa.moa.presentation.record.recorder.platform

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State

data class RecorderState(
    val isRecording: Boolean = false,
    val isPaused: Boolean = false,
    val totalRecordMs: Long = 0L,
    val amplitude: Float = 0f,
    val filePath: String? = null,
    val error: String? = null,
)

interface RecorderController {
    val state: State<RecorderState>
    fun start()
    fun pause()
    fun resume()
    fun stop(): String?
    fun release()
}

@Composable
expect fun rememberRecorderController(): RecorderController

