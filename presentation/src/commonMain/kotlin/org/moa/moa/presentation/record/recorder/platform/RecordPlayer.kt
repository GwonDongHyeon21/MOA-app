package org.moa.moa.presentation.record.recorder.platform

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State

data class PlayerState(
    val isLoaded: Boolean = false,
    val isPlaying: Boolean = false,
    val isEnded: Boolean = false,
    val currentPlayMs: Long = 0L,
    val totalPlayMs: Long = 0L,
    val amplitude: Float = 0f,
    val error: String? = null,
)

interface PlayerController {
    val state: State<PlayerState>
    fun load(path: String, autoPlay: Boolean = true)
    fun play()
    fun pause()
    fun seekTo(positionMs: Long)
    fun close()
}

@Composable
expect fun rememberPlayerController(): PlayerController