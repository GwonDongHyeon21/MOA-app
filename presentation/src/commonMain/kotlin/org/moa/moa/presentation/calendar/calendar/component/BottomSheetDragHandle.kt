package org.moa.moa.presentation.calendar.calendar.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import org.moa.moa.presentation.calendar.calendar.component.DragHandleDimens.sheetDragHandleHeight
import org.moa.moa.presentation.calendar.calendar.component.DragHandleDimens.sheetDragHandleRoundedCornerShape
import org.moa.moa.presentation.calendar.calendar.component.DragHandleDimens.sheetDragHandleTopPadding
import org.moa.moa.presentation.calendar.calendar.component.DragHandleDimens.sheetDragHandleWidth
import org.moa.moa.presentation.ui.theme.GRAY3

private object DragHandleDimens {
    val sheetDragHandleWidth = 68.dp
    val sheetDragHandleHeight = 3.dp
    val sheetDragHandleTopPadding = 5.dp
    val sheetDragHandleRoundedCornerShape = RoundedCornerShape(2.5.dp)
}

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