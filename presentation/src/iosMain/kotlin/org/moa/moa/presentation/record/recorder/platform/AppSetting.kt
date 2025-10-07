package org.moa.moa.presentation.record.recorder.platform

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import platform.Foundation.NSURL
import platform.UIKit.UIApplication
import platform.UIKit.UIApplicationOpenSettingsURLString

@Composable
actual fun AppSetting(
    isAppSetting: Boolean,
    onFalse: () -> Unit,
) {
    if (!isAppSetting) return
    LaunchedEffect(isAppSetting) {
        val url = NSURL(string = UIApplicationOpenSettingsURLString)
        UIApplication.sharedApplication.openURL(url)
        onFalse()
    }
}