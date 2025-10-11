package org.moa.moa.presentation.calendar.calendar.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import moa.presentation.generated.resources.Res
import moa.presentation.generated.resources.bottom_sheet_content_background
import org.jetbrains.compose.resources.painterResource
import org.moa.moa.presentation.calendar.CalendarDimens.verticalPadding
import org.moa.moa.presentation.ui.theme.APP_HORIZONTAL_PADDING1

@Composable
fun BottomSheetContentBackground(modifier: Modifier) {
    Card(
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        modifier = modifier
    ) {
        Image(
            painter = painterResource(Res.drawable.bottom_sheet_content_background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}