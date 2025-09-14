package org.moa.moa.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp

@Composable
fun dpToPx(value: Dp): Float {
    val density = LocalDensity.current
    return with(density) { value.toPx() }
}