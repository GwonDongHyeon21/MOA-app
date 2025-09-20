package org.moa.moa.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import moa.presentation.generated.resources.Res
import moa.presentation.generated.resources.floating_action_button
import moa.presentation.generated.resources.text_field
import org.jetbrains.compose.resources.painterResource
import org.moa.moa.presentation.ui.theme.Strings
import org.moa.moa.presentation.ui.theme.transparent

@Composable
fun MOAFloatingActionButton(
    modifier: Modifier,
    imageBytes: ByteArray?,
    onClick: () -> Unit,
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        if (imageBytes == null) {
            Box(
                modifier = modifier
                    .paint(
                        painter = painterResource(Res.drawable.text_field),
                        contentScale = ContentScale.FillBounds
                    )
                    .padding(horizontal = 5.dp)
                    .padding(start = 5.dp, end = 15.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(text = Strings.image_guideline)
            }
        }

        IconButton(
            onClick = { onClick() },
            modifier = modifier.size(60.dp),
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = transparent
            )
        ) {
            Image(
                painter = painterResource(Res.drawable.floating_action_button),
                contentDescription = "AddImageButton",
            )
        }
    }
}