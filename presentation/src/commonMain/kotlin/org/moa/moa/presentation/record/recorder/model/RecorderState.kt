package org.moa.moa.presentation.record.recorder.model

sealed class RecorderState {
    data object RECORD : RecorderState()
    data class PLAYING(val recordFile: String) : RecorderState()
    data object SUCCESS : RecorderState()
    data object LOADING : RecorderState()
    data object ERROR : RecorderState()
}

enum class RecordMode {
    DEFAULT,
    START,
    PAUSE,
}