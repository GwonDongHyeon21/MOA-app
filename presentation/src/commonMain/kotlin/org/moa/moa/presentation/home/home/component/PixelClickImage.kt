package org.moa.moa.presentation.home.home.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toPixelMap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize
import kotlin.math.roundToInt

@Composable
fun PixelClickImage(
    image: ImageBitmap,
    modifier: Modifier,
    alphaThreshold: Float = 0.1f,
    onClick: () -> Unit,
) {
    var size by remember { mutableStateOf(IntSize.Zero) }
    val pixelMap = remember(image) { image.toPixelMap() }

    Canvas(
        modifier = modifier
            .onSizeChanged { size = it }
            .pointerInput(image, size, alphaThreshold) {
                awaitEachGesture {
                    val down = awaitFirstDown(requireUnconsumed = false)
                    val pos = down.position
                    if (size.width > 0 && size.height > 0) {
                        val bx = (pos.x * image.width / size.width).roundToInt()
                        val by = (pos.y * image.height / size.height).roundToInt()
                        if (bx in 0 until image.width && by in 0 until image.height) {
                            if (pixelMap[bx, by].alpha > alphaThreshold) onClick()
                        }
                    }
                }
            }
    ) {
        drawImage(image = image, dstSize = size)
    }
}