package org.moa.moa.presentation.record.recorder.platform

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat

@Composable
actual fun rememberPermissionState(): PermissionState {
    val context = androidx.compose.ui.platform.LocalContext.current
    var granted by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO)
                    == PackageManager.PERMISSION_GRANTED
        )
    }

    var lastCallback by remember { mutableStateOf<((Boolean) -> Unit)?>(null) }

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { ok ->
        granted = ok
        lastCallback?.invoke(!ok)
        lastCallback = null
    }

    return object : PermissionState {
        override val status: PermissionStatus
            get() = if (granted) PermissionStatus.Granted else PermissionStatus.Denied

        override fun launchPermissionRequest(onResult: (Boolean) -> Unit) {
            lastCallback = onResult
            launcher.launch(Manifest.permission.RECORD_AUDIO)
        }
    }
}