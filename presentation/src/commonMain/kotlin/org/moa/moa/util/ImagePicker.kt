package org.moa.moa.util

import androidx.compose.runtime.Composable

@Composable
expect fun rememberImagePicker(onImagePicked: (ByteArray) -> Unit): ImagePicker

interface ImagePicker {
    fun pickImage()
}