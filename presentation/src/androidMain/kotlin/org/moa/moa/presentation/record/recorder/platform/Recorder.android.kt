package org.moa.moa.presentation.record.recorder.platform

import android.content.Context
import android.media.MediaRecorder
import android.os.Build
import android.os.Environment
import android.os.SystemClock
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
import java.io.File
import java.lang.Math.log10
import java.lang.Math.pow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private class AndroidRecorder(private val app: Context) : RecorderController {
    private var recorder: MediaRecorder? = null
    private var timerJob: Job? = null
    private var startRecordMs: Long = 0L
    private var totalRecordMs: Long = 0L

    private val _state = mutableStateOf(RecorderState())
    override val state: State<RecorderState> = _state

    override fun start() {
        if (_state.value.isRecording) return
        val file = outputFile()
        try {
            val rec = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                MediaRecorder(app)
            } else {
                @Suppress("DEPRECATION")
                MediaRecorder()
            }
            recorder = rec
            rec.setAudioSource(MediaRecorder.AudioSource.MIC)
            rec.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            rec.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            rec.setAudioEncodingBitRate(128_000)
            rec.setAudioSamplingRate(44_100)
            rec.setOutputFile(file.absolutePath)
            rec.prepare()
            rec.start()

            totalRecordMs = 0L
            startRecordMs = now
            _state.value = RecorderState(
                isRecording = true,
                isPaused = false,
                totalRecordMs = 0L,
                filePath = file.absolutePath
            )
            startTimer()
        } catch (t: Throwable) {
            _state.value = _state.value.copy(error = t.message)
            stop()
        }
    }

    override fun pause() {
        val state = _state.value
        if (!state.isRecording || state.isPaused) return
        try {
            recorder?.pause()
            totalRecordMs += now - startRecordMs
            stopTimer()
            _state.value = state.copy(
                isPaused = true,
                totalRecordMs = totalRecordMs
            )
        } catch (t: Throwable) {
            _state.value = state.copy(error = t.message)
        }
    }

    override fun resume() {
        val state = _state.value
        if (!state.isRecording || !state.isPaused) return
        try {
            recorder?.resume()
            startRecordMs = now
            _state.value = state.copy(isPaused = false)
            startTimer()
        } catch (t: Throwable) {
            _state.value = state.copy(error = t.message)
        }
    }

    override fun stop(): String? {
        stopTimer()
        val state = _state.value
        if (state.isRecording && !state.isPaused) {
            totalRecordMs += now - startRecordMs
        }
        try {
            recorder?.apply {
                try {
                    stop()
                } catch (_: Throwable) {
                }
                try {
                    reset()
                } catch (_: Throwable) {
                }
                try {
                    release()
                } catch (_: Throwable) {
                }
            }
        } catch (t: Throwable) {
            _state.value = state.copy(error = t.message)
        }
        recorder = null
        _state.value = state.copy(
            isRecording = false,
            isPaused = false,
            totalRecordMs = totalRecordMs
        )
        return _state.value.filePath
    }

    private fun outputFile(): File {
        val directory = app.getExternalFilesDir(Environment.DIRECTORY_MUSIC) ?: app.filesDir
        val name =
            "MOA" + SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date()) + ".m4a"
        return File(directory, name)
    }

    override fun release() {
        stopTimer()
        try {
            recorder?.release()
        } catch (t: Throwable) {
            _state.value = _state.value.copy(error = t.message)
        }
        recorder = null
        totalRecordMs = 0L
        _state.value = RecorderState()
    }

    private fun startTimer() {
        stopTimer()
        timerJob = CoroutineScope(Dispatchers.Main.immediate).launch {
            val state = _state.value
            while (isActive && state.isRecording && !state.isPaused) {
                val amplitude = try {
                    recorder?.maxAmplitude ?: 0
                } catch (_: Throwable) {
                    0
                }

                val level = run {
                    val db =
                        if (amplitude > 0) (20 * log10(amplitude.toDouble() / 32767.0)).toFloat() else -60f
                    val minDb = 50f
                    val clamped = db.fastCoerceIn(-minDb, 0f)
                    val normalized = (clamped + minDb) / minDb
                    pow(normalized.toDouble(), 1.5).toFloat().fastCoerceIn(0f, 1f)
                }

                _state.value = state.copy(
                    totalRecordMs = totalRecordMs + now - startRecordMs,
                    amplitude = level
                )
                delay(100)
            }
        }
    }

    private fun stopTimer() {
        timerJob?.cancel()
        timerJob = null
    }

    private val now get() = SystemClock.elapsedRealtime()
}

@Composable
actual fun rememberRecorderController(): RecorderController {
    val ctx = androidx.compose.ui.platform.LocalContext.current.applicationContext
    return remember(ctx) { AndroidRecorder(ctx) }
}