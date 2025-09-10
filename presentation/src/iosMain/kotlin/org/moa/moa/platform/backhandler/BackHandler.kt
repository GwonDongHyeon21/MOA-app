package org.moa.moa.platform.backhandler

import androidx.compose.runtime.Composable

@Composable
actual fun BackStackHandler(enable: Boolean, onBack: () -> Unit) {
    // Empty for iOS
}