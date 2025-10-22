package org.moa.moa.presentation.component

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.moa.moa.presentation.ui.theme.BLACK
import kotlin.math.abs

@Composable
fun BounceImage(
    painter: Painter,
    modifier: Modifier = Modifier,
    amplitude: Dp = 28.dp,           // 얼마나 위로 튈지
    periodMs: Int = 1200,            // 한 번 점프 시간
) {
    val transition = rememberInfiniteTransition(label = "bounce")
    // progress: 0f → 1f (키프레임으로 자연스러운 타이밍 구성)
    val progress by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(periodMs, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "progress"
    )

    // progress → y, scale 변환
    val upDown = when {
        progress < 0.45f -> -(progress / 0.45f) // 상승 구간: 0 → -1
        progress < 0.9f -> -(1f - (progress - 0.45f) / 0.45f) // 하강 구간: -1 → 0
        else -> 0f // 착지 및 잔진동 구간
    }

    // y 이동량 (음수면 위로)
    val translateY = amplitude * upDown

    // 그림자: 높이 높을수록 작고 옅게, 착지 때 넓고 진하게
    val h = abs(upDown)
    val shadowScale = 1f + 0.35f * h
    val shadowAlpha = 0.4f - 0.2f * h

    Box(
        modifier = modifier,
        contentAlignment = Alignment.BottomCenter
    ) {
        // 타원 그림자
        val height = 20.dp
        Canvas(
            modifier = Modifier
                .fillMaxWidth(0.35f * shadowScale)
                .height(height)
        ) {
            drawOval(
                color = BLACK.copy(alpha = shadowAlpha)
            )
        }

        // 이미지
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier
                .graphicsLayer { translationY = translateY.toPx() }
                .padding(bottom = height / 2)
        )
    }
}
