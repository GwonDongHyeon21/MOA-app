package org.moa.moa.presentation.record.textimage.platform

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
actual fun rememberImagePicker(onImagePicked: (ByteArray) -> Unit): ImagePicker {
    val context = LocalContext.current
    val contentResolver = context.contentResolver

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            uri?.let {
                val bytes = contentResolver.openInputStream(it)?.readBytes()
                if (bytes != null) {
                    onImagePicked(bytes)
                }
            }
        }
    )

    return object : ImagePicker {
        override fun pickImage() {
            launcher.launch("image/*")
        }
    }
}