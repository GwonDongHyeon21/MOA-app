package org.moa.moa.presentation.calendar.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.moa.moa.presentation.ui.theme.GRAY1
import org.moa.moa.presentation.ui.theme.Strings
import org.moa.moa.presentation.ui.theme.textStyle1

@Composable
fun BottomSheetContentPlaceholder(modifier: Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = Strings.empty_record_placeholder,
            style = textStyle1,
            color = GRAY1
        )
    }
}