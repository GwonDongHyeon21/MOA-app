package org.moa.moa.presentation.record.recorder.platform

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import platform.AVFAudio.AVAudioSession
import platform.AVFAudio.AVAudioSessionRecordPermissionDenied
import platform.AVFAudio.AVAudioSessionRecordPermissionGranted
import platform.AVFAudio.AVAudioSessionRecordPermissionUndetermined
import platform.darwin.dispatch_async
import platform.darwin.dispatch_get_main_queue

@Composable
actual fun rememberPermissionState(): PermissionState {
    var current by remember { mutableStateOf(readPermissionStatus()) }
    var lastCallback by remember { mutableStateOf<((Boolean) -> Unit)?>(null) }

    return object : PermissionState {
        override val status: PermissionStatus
            get() = current

        override fun launchPermissionRequest(onResult: (Boolean) -> Unit) {
            lastCallback = onResult
            AVAudioSession.sharedInstance().requestRecordPermission { granted: Boolean ->
                dispatch_async(dispatch_get_main_queue()) {
                    current = if (granted) PermissionStatus.Granted else PermissionStatus.Denied
                    lastCallback?.invoke(!granted)
                    lastCallback = null
                }
            }
        }
    }
}

private fun readPermissionStatus(): PermissionStatus {
    val permission = AVAudioSession.sharedInstance().recordPermission
    return when (permission) {
        AVAudioSessionRecordPermissionGranted -> PermissionStatus.Granted
        AVAudioSessionRecordPermissionDenied -> PermissionStatus.Denied
        AVAudioSessionRecordPermissionUndetermined -> PermissionStatus.Denied
        else -> PermissionStatus.Denied
    }
}