package org.moa.moa.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import platform.Foundation.NSData
import platform.Foundation.NSURL
import platform.Foundation.dataWithContentsOfURL
import platform.UIKit.UIApplication
import platform.UIKit.UIImagePickerController
import platform.UIKit.UIImagePickerControllerDelegateProtocol
import platform.UIKit.UIImagePickerControllerImageURL
import platform.UIKit.UIImagePickerControllerSourceType
import platform.UIKit.UINavigationControllerDelegateProtocol
import platform.darwin.NSObject
import platform.posix.memcpy

@Composable
actual fun rememberImagePicker(onImagePicked: (ByteArray) -> Unit): ImagePicker {
    val imagePickerDelegate = remember {
        ImagePickerDelegate(onImagePicked)
    }
    val imagePickerController = remember {
        UIImagePickerController().apply {
            sourceType =
                UIImagePickerControllerSourceType.UIImagePickerControllerSourceTypePhotoLibrary
            delegate = imagePickerDelegate
        }
    }

    return object : ImagePicker {
        override fun pickImage() {
            UIApplication.sharedApplication.keyWindow?.rootViewController?.presentViewController(
                imagePickerController,
                animated = true,
                completion = null
            )
        }
    }
}

class ImagePickerDelegate(
    private val onImagePicked: (ByteArray) -> Unit,
) : NSObject(), UIImagePickerControllerDelegateProtocol, UINavigationControllerDelegateProtocol {

    override fun imagePickerController(
        picker: UIImagePickerController,
        didFinishPickingMediaWithInfo: Map<Any?, *>,
    ) {
        val imageUrl = didFinishPickingMediaWithInfo[UIImagePickerControllerImageURL] as? NSURL
        if (imageUrl != null) {
            val imageData = NSData.dataWithContentsOfURL(imageUrl)
            if (imageData != null) {
                onImagePicked(imageData.toByteArray())
            }
        }
        picker.dismissViewControllerAnimated(true, null)
    }

    override fun imagePickerControllerDidCancel(picker: UIImagePickerController) {
        picker.dismissViewControllerAnimated(true, null)
    }
}

@OptIn(ExperimentalForeignApi::class)
fun NSData.toByteArray(): ByteArray {
    val byteArray = ByteArray(this.length.toInt())
    byteArray.usePinned { pinned ->
        memcpy(pinned.addressOf(0), this.bytes, this.length)
    }
    return byteArray
}