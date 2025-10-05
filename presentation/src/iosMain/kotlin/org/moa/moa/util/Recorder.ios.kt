package org.moa.moa.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.util.fastCoerceIn
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import platform.AVFAudio.AVAudioQualityHigh
import platform.AVFAudio.AVAudioRecorder
import platform.AVFAudio.AVAudioSession
import platform.AVFAudio.AVAudioSessionCategoryOptionDefaultToSpeaker
import platform.AVFAudio.AVAudioSessionCategoryPlayAndRecord
import platform.AVFAudio.AVEncoderAudioQualityKey
import platform.AVFAudio.AVFormatIDKey
import platform.AVFAudio.AVNumberOfChannelsKey
import platform.AVFAudio.AVSampleRateKey
import platform.AVFAudio.setActive
import platform.CoreAudioTypes.kAudioFormatMPEG4AAC
import platform.Foundation.NSDate
import platform.Foundation.NSDateFormatter
import platform.Foundation.NSLocale
import platform.Foundation.NSTemporaryDirectory
import platform.Foundation.NSURL
import platform.Foundation.localeWithLocaleIdentifier
import platform.Foundation.timeIntervalSince1970
import kotlin.math.pow

private class IOSRecorder : RecorderController {
    private var recorder: AVAudioRecorder? = null
    private var timerJob: Job? = null
    private var startRecordMs: Long = 0L
    private var totalRecordMs: Long = 0L

    private val _state = mutableStateOf(RecorderState())
    override val state: State<RecorderState> get() = _state

    private fun outputFile(): NSURL {
        val formatter = NSDateFormatter().apply {
            dateFormat = "yyyyMMdd_HHmmss"
            locale = NSLocale.localeWithLocaleIdentifier("ko_KO")
        }
        val name = "MOA_${formatter.stringFromDate(NSDate())}.m4a"
        val directory = NSTemporaryDirectory()
        return NSURL.fileURLWithPath(directory + name)
    }

    @OptIn(ExperimentalForeignApi::class)
    override fun start() {
        if (_state.value.isRecording) return

        val session = AVAudioSession.sharedInstance()
        session.setCategory(
            category = AVAudioSessionCategoryPlayAndRecord,
            withOptions = AVAudioSessionCategoryOptionDefaultToSpeaker,
            error = null
        )
        session.setActive(true, null)

        val url = outputFile()
        val settings: Map<Any?, Any?> = mapOf(
            AVFormatIDKey to kAudioFormatMPEG4AAC,
            AVSampleRateKey to 44100.0,
            AVNumberOfChannelsKey to 1,
            AVEncoderAudioQualityKey to AVAudioQualityHigh
        )

        val rec = AVAudioRecorder(
            uRL = url,
            settings = settings,
            error = null
        )
        if (!rec.prepareToRecord()) {
            _state.value = _state.value.copy(error = "prepareToRecord() failed")
            return
        }

        recorder = rec
        rec.meteringEnabled = true

        totalRecordMs = 0L
        rec.record()

        startRecordMs = now
        _state.value = RecorderState(
            isRecording = true,
            isPaused = false,
            totalRecordMs = 0L,
            filePath = url.path
        )

        startTimer()
    }

    override fun pause() {
        val state = _state.value
        if (!state.isRecording || state.isPaused) return
        recorder?.let { record ->
            record.pause()
            totalRecordMs += now - startRecordMs
            stopTimer()
            _state.value = state.copy(isPaused = true, totalRecordMs = totalRecordMs)
        }
    }

    override fun resume() {
        val state = _state.value
        if (!state.isRecording || !state.isPaused) return
        recorder?.let { r ->
            r.record()
            startRecordMs = now
            _state.value = state.copy(isPaused = false)
            startTimer()
        }
    }

    override fun stop(): String? {
        stopTimer()
        val state = _state.value
        if (state.isRecording && !state.isPaused) {
            totalRecordMs += now - startRecordMs
        }
        try {
            recorder?.stop()
        } catch (t: Throwable) {
            _state.value = state.copy(error = t.message)
        }
        _state.value = state.copy(
            isRecording = false,
            isPaused = false,
            totalRecordMs = totalRecordMs
        )
        return _state.value.filePath
    }

    @OptIn(ExperimentalForeignApi::class)
    override fun release() {
        stopTimer()
        try {
            recorder?.stop()
        } catch (t: Throwable) {
            _state.value = _state.value.copy(error = t.message)
        }
        recorder = null
        AVAudioSession.sharedInstance().setActive(false, null)

        totalRecordMs = 0L
        _state.value = RecorderState()
    }

    private fun startTimer() {
        stopTimer()
        timerJob = CoroutineScope(Dispatchers.Main.immediate).launch {
            while (isActive) {
                val state = _state.value
                if (!state.isRecording || state.isPaused) break

                val recorder = recorder ?: break
                recorder.updateMeters()

                val db = recorder.averagePowerForChannel(0u)
                val level = decibelToLinear(db)

                _state.value = state.copy(
                    totalRecordMs = totalRecordMs + now - startRecordMs,
                    amplitude = level
                )
                delay(100)
            }
        }
    }

    private fun decibelToLinear(db: Float): Float {
        if (db < -50f) return 0f
        val minDb = -50f
        val clamped = db.coerceIn(minDb, 0f)
        val normalized = (clamped - minDb) / -minDb
        return normalized.pow(1.5f).fastCoerceIn(0f, 1f)
    }

    private fun stopTimer() {
        timerJob?.cancel()
        timerJob = null
    }

    private val now: Long get() = (NSDate().timeIntervalSince1970 * 1000.0).toLong()
}

@Composable
actual fun rememberRecorderController(): RecorderController {
    return remember { IOSRecorder() }
}