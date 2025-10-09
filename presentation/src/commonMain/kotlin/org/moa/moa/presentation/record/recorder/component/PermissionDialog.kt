package org.moa.moa.presentation.record.recorder.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import org.moa.moa.presentation.ui.theme.Strings

@Composable
fun PermissionDialog(
    onAppSetting: () -> Unit,
    onBack: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = { onBack() },
        title = { Text(text = Strings.mic_permission) },
        text = { Text(text = Strings.mic_permission_guideline) },
        confirmButton = {
            TextButton(
                onClick = {
                    onAppSetting()
                    onBack()
                }
            ) {
                Text(text = Strings.setting)
            }
        },
        dismissButton = {
            TextButton(onClick = { onBack() }) {
                Text(text = Strings.cancel)
            }
        }
    )
}