package org.moa.moa.presentation.record.recorder.platform

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.memScoped
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import platform.AVFAudio.AVAudioPlayer
import platform.AVFAudio.AVAudioPlayerDelegateProtocol
import platform.Foundation.NSURL
import platform.darwin.NSObject
import kotlin.math.pow

class IOSPlayer : PlayerController {
    private var player: AVAudioPlayer? = null
    private var timerJob: kotlinx.coroutines.Job? = null
    private val scope = kotlinx.coroutines.CoroutineScope(kotlinx.coroutines.Dispatchers.Main)

    private val _state = mutableStateOf(PlayerState())
    override val state: State<PlayerState> get() = _state

    @OptIn(ExperimentalForeignApi::class)
    override fun load(path: String, autoPlay: Boolean) {
        close()
        _state.value = PlayerState()

        try {
            memScoped {
                val url = NSURL.fileURLWithPath(path)
                val ap = AVAudioPlayer(contentsOfURL = url, error = null)

                player = ap
                ap.prepareToPlay()
                ap.meteringEnabled = true
                _state.value = _state.value.copy(
                    totalPlayMs = (ap.duration * 1000).toLong(),
                    isLoaded = true,
                    isEnded = false
                )

                ap.setDelegate(object : NSObject(), AVAudioPlayerDelegateProtocol {
                    override fun audioPlayerDidFinishPlaying(
                        player: AVAudioPlayer,
                        successfully: Boolean,
                    ) {
                        _state.value = _state.value.copy(
                            currentPlayMs = _state.value.totalPlayMs,
                            isPlaying = false,
                            isEnded = true
                        )
                    }
                })

                if (autoPlay) {
                    ap.play()
                    _state.value = _state.value.copy(isPlaying = true)
                    startTimer()
                }
            }
        } catch (e: Throwable) {
            _state.value = _state.value.copy(error = e.message)
        }
    }

    private fun startTimer() {
        timerJob?.cancel()
        timerJob = scope.launch {
            while (isActive && _state.value.isPlaying) {
                val ap = player ?: break
                ap.updateMeters()

                val db = ap.averagePowerForChannel(0u)
                val level = dbToLinear0to1(db)

                _state.value = _state.value.copy(
                    currentPlayMs = (ap.currentTime * 1000).toLong(),
                    amplitude = level
                )
                delay(100)
            }
        }
    }

    private fun dbToLinear0to1(db: Float): Float {
        val minDb = 50f
        if (db <= -minDb) return 0f
        val norm = (db + minDb) / minDb
        return norm.toDouble().pow(1.5).toFloat().coerceIn(0f, 1f)
    }

    override fun play() {
        val ap = player ?: return
        if (!_state.value.isLoaded || _state.value.isPlaying) return
        ap.play()
        _state.value = _state.value.copy(isPlaying = true, isEnded = false)
        startTimer()
    }

    override fun pause() {
        player?.pause()
        timerJob?.cancel()
        _state.value = _state.value.copy(isPlaying = false)
    }

    override fun seekTo(positionMs: Long) {
        player?.currentTime = positionMs.toDouble() / 1000.0
        _state.value =
            _state.value.copy(currentPlayMs = positionMs, isPlaying = false)
    }

    override fun close() {
        timerJob?.cancel()
        player?.stop()
        player = null
        _state.value = PlayerState(isLoaded = false, isPlaying = false)
    }
}

@Composable
actual fun rememberPlayerController(): PlayerController {
    return remember { IOSPlayer() }
}