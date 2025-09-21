package org.moa.moa.presentation.record.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import moa.presentation.generated.resources.Res
import moa.presentation.generated.resources.cancel
import org.jetbrains.compose.resources.painterResource
import org.moa.moa.presentation.ui.theme.APP_HORIZONTAL_PADDING1
import org.moa.moa.presentation.ui.theme.WHITE

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageDialog(
    modifier: Modifier,
    imageBytes: ByteArray,
    roundedCornerShape: RoundedCornerShape,
    isImageDialogExpanded: Boolean,
    onDismissRequest: () -> Unit,
) {
    if (isImageDialogExpanded) {
        BasicAlertDialog(
            onDismissRequest = { onDismissRequest() },
        ) {
            Column(horizontalAlignment = Alignment.End) {
                Icon(
                    painter = painterResource(Res.drawable.cancel),
                    contentDescription = "CancelImage",
                    modifier = modifier
                        .padding(5.dp)
                        .size(24.dp)
                        .clickable(
                            onClick = { onDismissRequest() },
                            interactionSource = null,
                            indication = null
                        ),
                    tint = WHITE
                )

                Spacer(modifier = modifier.height(20.dp))
                AsyncImage(
                    model = imageBytes,
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(roundedCornerShape)
                        .background(WHITE)
                        .padding(APP_HORIZONTAL_PADDING1)
                        .clip(roundedCornerShape)
                        .clickable { onDismissRequest() }
                )
            }
        }
    }
}