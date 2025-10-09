package org.moa.moa.presentation.calendar.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import org.moa.moa.presentation.calendar.CalendarDimens.sheetDragHandleHeight
import org.moa.moa.presentation.calendar.CalendarDimens.sheetDragHandleRoundedCornerShape
import org.moa.moa.presentation.calendar.CalendarDimens.sheetDragHandleTopPadding
import org.moa.moa.presentation.calendar.CalendarDimens.sheetDragHandleWidth
import org.moa.moa.presentation.ui.theme.GRAY3

@Composable
fun BottomSheetDragHandle(modifier: Modifier) {
    Box(
        modifier = modifier
            .padding(top = sheetDragHandleTopPadding)
            .size(sheetDragHandleWidth, sheetDragHandleHeight)
            .clip(sheetDragHandleRoundedCornerShape)
            .background(GRAY3)
    )
}