package org.moa.moa.presentation.record.recorder.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import org.moa.moa.presentation.record.recorder.component.VisualizerDimens.BAR_COUNT
import org.moa.moa.presentation.record.recorder.component.VisualizerDimens.BASELINE
import org.moa.moa.presentation.record.recorder.component.VisualizerDimens.EMA_ALPHA
import org.moa.moa.presentation.record.recorder.component.VisualizerDimens.MAX_BAR_HEIGHT
import org.moa.moa.presentation.record.recorder.component.VisualizerDimens.VISUALIZER_TWEEN

object VisualizerDimens {
    const val BAR_COUNT = 14
    const val BASELINE = 0.1f
    const val EMA_ALPHA = 0.5f
    const val VISUALIZER_TWEEN = 100
    val MAX_BAR_HEIGHT = 300.dp
}

@Composable
fun RecordVisualizer(
    modifier: Modifier,
    isTicking: Boolean,
    currentLevel: Float,
) {
    val bars = remember {
        mutableStateListOf<Float>().apply {
            repeat(BAR_COUNT) { add(BASELINE) }
        }
    }

    var ema by remember { mutableStateOf(BASELINE) }
    fun smooth(v: Float): Float {
        val alpha = EMA_ALPHA
        ema = (alpha * v + (1 - alpha) * ema).coerceIn(0f, 1f)
        return ema
    }

    val latestLevel by rememberUpdatedState(currentLevel)

    LaunchedEffect(isTicking) {
        while (isTicking) {
            val next = smooth(latestLevel.coerceIn(0f, 1f)).coerceAtLeast(BASELINE)
            if (bars.isNotEmpty()) {
                bars.removeAt(0)
                bars.add(next)
            }
            delay(100)
        }
    }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.Bottom
    ) {
        bars.forEach { bar ->
            val barHeight by animateFloatAsState(
                targetValue = bar.coerceIn(BASELINE, 1f),
                animationSpec = tween(VISUALIZER_TWEEN),
                label = "barHeight"
            )
            Box(
                Modifier
                    .width(11.dp)
                    .height(MAX_BAR_HEIGHT * barHeight)
                    .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(100.dp))
            )
        }
    }
}