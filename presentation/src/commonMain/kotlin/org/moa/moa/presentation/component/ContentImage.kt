package org.moa.moa.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import moa.presentation.generated.resources.Res
import moa.presentation.generated.resources.top_logo
import org.jetbrains.compose.resources.painterResource
import org.moa.moa.presentation.component.ContentImageDimens.CONTENT_IMAGE_HEIGHT_FRACTION
import org.moa.moa.presentation.ui.theme.CORNER_RADIUS
import org.moa.moa.presentation.ui.theme.GRAY3
import org.moa.moa.presentation.ui.theme.GRAY4

private object ContentImageDimens {
    const val CONTENT_IMAGE_HEIGHT_FRACTION = 0.4f
}

@Composable
fun ContentImage(
    modifier: Modifier,
    images: List<String>?,
) {
    images?.let {
        LazyRow(
            modifier = modifier
                .padding(vertical = 8.dp, horizontal = 25.dp)
                .fillMaxHeight(CONTENT_IMAGE_HEIGHT_FRACTION),
            horizontalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            items(it) { image ->
                AsyncImage(
                    model = image,
                    contentDescription = "RecordImage",
                    modifier = Modifier
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(CORNER_RADIUS)),
                    placeholder = painterResource(Res.drawable.top_logo),
                    error = painterResource(Res.drawable.top_logo),
                    contentScale = ContentScale.FillHeight
                )
            }
        }
    } ?: run {
        Image(
            painter = painterResource(Res.drawable.top_logo),
            contentDescription = null,
            modifier = modifier
                .fillMaxHeight(CONTENT_IMAGE_HEIGHT_FRACTION)
                .padding(vertical = 8.dp, horizontal = 25.dp)
                .clip(RoundedCornerShape(CORNER_RADIUS))
                .background(GRAY4)
                .border(1.dp, GRAY3, RoundedCornerShape(CORNER_RADIUS)),
        )
    }
}