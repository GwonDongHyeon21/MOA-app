package org.moa.moa.platform.backhandler

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable

@Composable
actual fun BackStackHandler(enable:Boolean, onBack: () -> Unit) {
    BackHandler { onBack() }
}