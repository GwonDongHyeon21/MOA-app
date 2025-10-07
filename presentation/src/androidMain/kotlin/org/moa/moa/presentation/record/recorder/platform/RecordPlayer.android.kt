package org.moa.moa.presentation.record.recorder.platform

import android.media.MediaPlayer
import android.media.audiofx.Visualizer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.util.fastCoerceIn
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.lang.Integer.min
import java.lang.Math.log10
import java.lang.Math.pow
import java.lang.Math.sqrt

private class AndroidPlayer : PlayerController {
    private var mp: MediaPlayer? = null
    private var timerJob: Job? = null
    private var visualizer: Visualizer? = null
    private val scope = CoroutineScope(Dispatchers.Main.immediate)

    private val _state = mutableStateOf(PlayerState())
    override val state: State<PlayerState> get() = _state

    override fun load(path: String, autoPlay: Boolean) {
        close()
        _state.value = PlayerState()
        try {
            val player = MediaPlayer().apply {
                setDataSource(path)
                setAudioAttributes(
                    android.media.AudioAttributes.Builder()
                        .setUsage(android.media.AudioAttributes.USAGE_MEDIA)
                        .setContentType(android.media.AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build()
                )
                setOnErrorListener { _, what, extra ->
                    _state.value = _state.value.copy(
                        error = "MediaPlayer error: what=$what extra=$extra",
                        isLoaded = false
                    )
                    close()
                    true
                }
                setOnPreparedListener { it2 ->
                    _state.value = _state.value.copy(
                        totalPlayMs = it2.duration.toLong(),
                        isLoaded = true,
                        isEnded = false
                    )
                    startVisualizer(it2.audioSessionId)
                    if (autoPlay) {
                        it2.start()
                        _state.value = _state.value.copy(isPlaying = true)
                        startTimer()
                    }
                }
                setOnCompletionListener {
                    pause()
                    _state.value = _state.value.copy(
                        currentPlayMs = _state.value.totalPlayMs,
                        isPlaying = false,
                        isEnded = true,
                        amplitude = 0f
                    )
                }
                prepareAsync()
            }
            mp = player
        } catch (e: Exception) {
            _state.value = _state.value.copy(error = "Load error: ${e.message}")
        }
    }

    private var fadeFrame = 0

    private fun startVisualizer(sessionId: Int) {
        if (visualizer != null) return
        try {
            fadeFrame = 0
            visualizer = Visualizer(sessionId).apply {
                val range = Visualizer.getCaptureSizeRange()
                captureSize = min(range[1], 256)

                try {
                    setScalingMode(Visualizer.SCALING_MODE_AS_PLAYED)
                } catch (_: Throwable) {
                }

                setDataCaptureListener(
                    object : Visualizer.OnDataCaptureListener {
                        override fun onWaveFormDataCapture(
                            visualizer: Visualizer?,
                            waveform: ByteArray?,
                            samplingRate: Int,
                        ) {
                            if (waveform == null || waveform.isEmpty()) {
                                _state.value = _state.value.copy(amplitude = 0f)
                                return
                            }

                            var sumSq = 0.0
                            for (b in waveform) {
                                val centered = (b.toInt() and 0xFF) - 128
                                sumSq += centered * centered
                            }
                            val rms = sqrt(sumSq / waveform.size)
                            val linear = (rms / 127.0).fastCoerceIn(0.0, 1.0)
                            val db = if (linear > 0.0) (20.0 * log10(linear)).toFloat() else MIN_DB
                            var level = dbToLevel(db)

                            if (fadeFrame < WARMUP_FRAMES) {
                                level = 0f
                            } else if (fadeFrame < WARMUP_FRAMES + FADE_FRAMES) {
                                val t = (fadeFrame - WARMUP_FRAMES + 1) / FADE_FRAMES.toFloat()
                                level *= t.fastCoerceIn(0f, 1f)
                            }
                            fadeFrame++

                            _state.value = _state.value.copy(amplitude = level)
                        }

                        override fun onFftDataCapture(p0: Visualizer?, p1: ByteArray?, p2: Int) {}
                    },
                    Visualizer.getMaxCaptureRate() / 2,
                    true,
                    false
                )
                enabled = true
            }
        } catch (t: Throwable) {
            _state.value = _state.value.copy(error = "Visualizer error: ${t.message}")
        }
    }

    private fun dbToLevel(db: Float): Float {
        val adj = (db + CALIBRATION_DB).fastCoerceIn(MIN_DB, 0f)
        val norm = (adj - MIN_DB) / -MIN_DB
        return pow(norm.toDouble(), 1.2).toFloat().fastCoerceIn(0f, 1f)
    }

    private fun stopVisualizer() {
        visualizer?.enabled = false
        visualizer?.release()
        visualizer = null
    }

    private fun startTimer() {
        timerJob?.cancel()
        timerJob = scope.launch {
            while (isActive && _state.value.isPlaying) {
                _state.value = _state.value.copy(
                    currentPlayMs = mp?.currentPosition?.toLong() ?: 0L
                )
                delay(100)
            }
        }
    }

    override fun play() {
        val p = mp ?: return
        if (!_state.value.isLoaded || _state.value.isPlaying) return
        _state.value = _state.value.copy(isEnded = false)
        p.start()
        _state.value = _state.value.copy(isPlaying = true)
        startTimer()

        if (visualizer == null) startVisualizer(p.audioSessionId)
        else try {
            visualizer?.enabled = true
        } catch (_: Throwable) {
        }
    }

    override fun pause() {
        if (_state.value.isPlaying) mp?.pause()
        timerJob?.cancel()
        _state.value = _state.value.copy(isPlaying = false)
    }

    override fun seekTo(positionMs: Long) {
        mp?.seekTo(positionMs.toInt())
        _state.value = _state.value.copy(currentPlayMs = positionMs, isEnded = false)
    }

    override fun close() {
        timerJob?.cancel()
        stopVisualizer()
        try {
            mp?.release()
        } catch (_: Throwable) {
        }
        mp = null
        _state.value = PlayerState(isLoaded = false, isPlaying = false)
    }

    companion object {
        private const val CALIBRATION_DB = 8f
        private const val MIN_DB = -60f
        private const val WARMUP_FRAMES = 2
        private const val FADE_FRAMES = 6
    }
}

@Composable
actual fun rememberPlayerController(): PlayerController {
    return remember { AndroidPlayer() }
}