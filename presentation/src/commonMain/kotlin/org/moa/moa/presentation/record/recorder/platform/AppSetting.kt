package org.moa.moa.presentation.record.recorder.platform

import androidx.compose.runtime.Composable

@Composable
expect fun AppSetting(isAppSetting: Boolean, onFalse: () -> Unit)