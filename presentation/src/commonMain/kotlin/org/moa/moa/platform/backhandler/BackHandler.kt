package org.moa.moa.platform.backhandler

import androidx.compose.runtime.Composable

@Composable
expect fun BackStackHandler(enable: Boolean, onBack: () -> Unit)