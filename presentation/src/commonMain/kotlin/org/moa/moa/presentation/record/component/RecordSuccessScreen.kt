package org.moa.moa.presentation.record.component

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import moa.presentation.generated.resources.Res
import moa.presentation.generated.resources.disk_shape
import moa.presentation.generated.resources.star
import org.jetbrains.compose.resources.painterResource
import org.moa.moa.presentation.component.MOABackTopBar
import org.moa.moa.presentation.component.MOAButton
import org.moa.moa.presentation.ui.theme.GRAY1
import org.moa.moa.presentation.ui.theme.MAIN
import org.moa.moa.presentation.ui.theme.Strings
import org.moa.moa.presentation.ui.theme.WHITE
import org.moa.moa.util.formatDateTime

@Composable
fun RecordSuccessScreen(onClick: () -> Unit) {
    Scaffold(
        topBar = {
            MOABackTopBar(
                modifier = Modifier,
                onBack = { onClick() }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = formatDateTime(
                    dateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
                    pattern = Strings.date_time_format
                ),
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .border(2.dp, MAIN, RoundedCornerShape(100.dp))
                    .background(WHITE)
                    .padding(vertical = 12.dp, horizontal = 30.dp)
            )

            Spacer(modifier = Modifier.height(50.dp))
            ShiningImage(painter = painterResource(Res.drawable.star))

            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = Strings.add_record_text,
                fontSize = 17.sp,
                color = GRAY1
            )

            Spacer(modifier = Modifier.height(40.dp))
            MOAButton(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .height(60.dp)
                    .clip(RoundedCornerShape(58.dp)),
                text = Strings.confirm
            ) {
                onClick()
            }
        }
    }
}

@Composable
fun ShiningImage(
    painter: Painter,
    size: Dp = 300.dp,
    glowColor: Color = MAIN,
    glowBlur: Dp = 12.dp,
    glowAlpha: Float = 0.7f,
    glowScale: Float = 1.1f,
) {
    val infiniteTransition = rememberInfiniteTransition()

    val durationMillis = 500
    val animateGlowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.2f,
        targetValue = glowAlpha,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = durationMillis, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    val animateGlowScale by infiniteTransition.animateFloat(
        initialValue = 1.0f,
        targetValue = glowScale,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = durationMillis, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(
        modifier = Modifier.padding(glowBlur),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(Res.drawable.disk_shape),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .padding(bottom = glowBlur)
                .align(Alignment.BottomCenter)
        )

        Image(
            painter = painter,
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(size)
                .scale(animateGlowScale)
                .blur(glowBlur)
                .alpha(animateGlowAlpha),
            colorFilter = ColorFilter.tint(glowColor, BlendMode.SrcIn)
        )

        Image(
            painter = painter,
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier.size(size)
        )
    }
}