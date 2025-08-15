package org.moa.moa.platform.backhandler

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable

@Composable
actual fun PopBackStackHandler(onBack: () -> Unit) {
    BackHandler { onBack() }
}