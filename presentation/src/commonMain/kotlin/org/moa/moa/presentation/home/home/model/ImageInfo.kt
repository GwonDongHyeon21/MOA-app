package org.moa.moa.presentation.home.home.model

import androidx.compose.ui.Alignment
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.Dp
import org.jetbrains.compose.resources.DrawableResource

data class ImageInfo(
    val drawableRes: DrawableResource,
    val alignment: Alignment,
    val size: Dp,
    val offset: Offset,
)