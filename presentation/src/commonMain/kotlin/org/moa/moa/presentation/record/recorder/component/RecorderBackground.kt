package org.moa.moa.presentation.record.recorder.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import moa.presentation.generated.resources.Res
import moa.presentation.generated.resources.mic_light
import moa.presentation.generated.resources.record_text_background_right
import org.jetbrains.compose.resources.painterResource

@Composable
fun RecorderBackgroundSection(modifier: Modifier) {
    Box(modifier = modifier.fillMaxWidth()) {
        Image(
            painter = painterResource(Res.drawable.mic_light),
            contentDescription = null,
            modifier = modifier.align(Alignment.TopStart)
        )
        Image(
            painter = painterResource(Res.drawable.record_text_background_right),
            contentDescription = null,
            modifier = modifier.align(Alignment.TopEnd)
        )
    }
}