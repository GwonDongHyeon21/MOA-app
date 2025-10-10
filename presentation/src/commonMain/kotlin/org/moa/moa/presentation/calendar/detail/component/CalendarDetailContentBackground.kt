package org.moa.moa.presentation.calendar.detail.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import moa.presentation.generated.resources.Res
import moa.presentation.generated.resources.calendar_detail_background
import org.jetbrains.compose.resources.painterResource
import org.moa.moa.presentation.calendar.detail.CalendarDetailDimens.CalendarDetailRoundedCorner

@Composable
fun CalendarDetailContentBackground(modifier: Modifier) {
    Card(
        shape = CalendarDetailRoundedCorner,
        elevation = CardDefaults.cardElevation(3.dp)
    ) {
        Image(
            painter = painterResource(Res.drawable.calendar_detail_background),
            contentDescription = null,
            modifier = modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}