package org.moa.moa.presentation.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import org.moa.moa.presentation.ui.theme.WHITE

@Composable
fun MOADialog(
    title: String,
    text: String,
    confirmText: String,
    dismissText: String,
    onClickConfirm: () -> Unit,
    onClickDismiss: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = { onClickDismiss() },
        title = { Text(text = title) },
        text = { Text(text = text) },
        confirmButton = {
            TextButton(
                onClick = {
                    onClickConfirm()
                    onClickDismiss()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = WHITE
                )
            ) {
                Text(text = confirmText)
            }
        },
        dismissButton = {
            TextButton(onClick = { onClickDismiss() }) {
                Text(text = dismissText)
            }
        }
    )
}