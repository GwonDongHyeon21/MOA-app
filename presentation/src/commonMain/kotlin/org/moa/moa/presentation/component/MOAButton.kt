package org.moa.moa.presentation.component

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.moa.moa.presentation.ui.theme.GRAY2
import org.moa.moa.presentation.ui.theme.GRAY3

@Composable
fun MOAButton(
    modifier: Modifier,
    text: String,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    Button(
        onClick = { onClick() },
        modifier = modifier.height(62.dp),
        shape = RoundedCornerShape(15.dp),
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            disabledContainerColor = GRAY3,
            disabledContentColor = GRAY2
        )
    ) {
        Text(text = text)
    }
}