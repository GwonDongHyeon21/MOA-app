package org.moa.moa.presentation.sign.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import moa.presentation.generated.resources.Res
import moa.presentation.generated.resources.left_arrow_icon
import moa.presentation.generated.resources.top_logo
import org.jetbrains.compose.resources.painterResource
import org.moa.moa.presentation.ui.theme.IVORY
import org.moa.moa.presentation.ui.theme.MAIN

@Composable
fun SignUpTopBar(progress: Int, onClick: () -> Unit) {
    Column {
        Box(modifier = Modifier.fillMaxWidth()) {
            IconButton(
                onClick = { onClick() },
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(
                    painter = painterResource(Res.drawable.left_arrow_icon),
                    contentDescription = "left_arrow_icon"
                )
            }
            Image(
                painter = painterResource(Res.drawable.top_logo),
                contentDescription = "top_logo",
                contentScale = ContentScale.Fit,
                modifier = Modifier.height(30.dp).align(Alignment.Center)
            )
        }

        Spacer(Modifier.height(10.dp))
        LinearProgressIndicator(
            progress = { progress.toFloat() / 3 },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
                .height(4.dp)
                .clip(RoundedCornerShape(100.dp)),
            color = MAIN,
            trackColor = IVORY,
        )
    }
}