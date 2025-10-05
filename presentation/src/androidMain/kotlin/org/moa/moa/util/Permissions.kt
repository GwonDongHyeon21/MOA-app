package org.moa.moa.util

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
    val ctx = androidx.compose.ui.platform.LocalContext.current
    var granted by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(ctx, Manifest.permission.RECORD_AUDIO)
                    == PackageManager.PERMISSION_GRANTED
        )
    }
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { ok -> granted = ok }

    return object : PermissionState {
        override val status: PermissionStatus
            get() = if (granted) PermissionStatus.Granted else PermissionStatus.Denied

        override fun launchPermissionRequest() {
            launcher.launch(Manifest.permission.RECORD_AUDIO)
        }
    }
}