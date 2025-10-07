package org.moa.moa.presentation.record.recorder.platform

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext

@Composable
actual fun AppSetting(
    isAppSetting: Boolean,
    onFalse: () -> Unit,
) {
    if (!isAppSetting) return
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        val intent = Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package", context.packageName, null)
        ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
        onFalse()
    }
}