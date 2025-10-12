package org.moa.moa.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import moa.presentation.generated.resources.Res
import moa.presentation.generated.resources.calendar_detail_background
import org.jetbrains.compose.resources.painterResource
import org.moa.moa.presentation.component.ContentBackgroundDimens.contentRoundedCornerShape

private object ContentBackgroundDimens{
    val contentRoundedCornerShape = RoundedCornerShape(25.dp)
}

@Composable
fun ContentBackground(modifier: Modifier) {
    Card(
        shape = contentRoundedCornerShape,
        elevation = CardDefaults.cardElevation(3.dp)
    ) {
        Image(
            painter = painterResource(Res.drawable.calendar_detail_background),
            contentDescription = null,
            modifier = modifier,
            contentScale = ContentScale.Crop
        )
    }
}