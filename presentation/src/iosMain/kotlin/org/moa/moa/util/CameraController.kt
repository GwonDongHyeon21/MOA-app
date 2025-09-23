package org.moa.moa.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.suspendCancellableCoroutine
import platform.UIKit.UIApplication
import platform.UIKit.UIImage
import platform.UIKit.UIImageJPEGRepresentation
import platform.UIKit.UIImagePickerController
import platform.UIKit.UIImagePickerControllerDelegateProtocol
import platform.UIKit.UIImagePickerControllerOriginalImage
import platform.UIKit.UIImagePickerControllerSourceType
import platform.UIKit.UINavigationControllerDelegateProtocol
import platform.UIKit.UIWindowScene
import platform.darwin.NSObject
import platform.darwin.dispatch_async
import platform.darwin.dispatch_get_main_queue
import kotlin.coroutines.resume

class CameraImage(
    var cont: CancellableContinuation<ByteArray?>?,
    var onDone: () -> Unit,
) : NSObject(), UIImagePickerControllerDelegateProtocol, UINavigationControllerDelegateProtocol {

    override fun imagePickerController(
        picker: UIImagePickerController,
        didFinishPickingMediaWithInfo: Map<Any?, *>,
    ) {
        val uiImage =
            didFinishPickingMediaWithInfo[UIImagePickerControllerOriginalImage] as? UIImage
        val data = uiImage?.let { UIImageJPEGRepresentation(it, 0.9) }
        cont?.resume(data?.toByteArray())
        cont = null
        picker.dismissViewControllerAnimated(true, completion = null)
        onDone()
    }

    override fun imagePickerControllerDidCancel(picker: UIImagePickerController) {
        cont?.resume(null)
        cont = null
        picker.dismissViewControllerAnimated(true, completion = null)
        onDone()
    }
}

private class IOSCameraController : CameraController {

    private var delegateRef: CameraImage? = null

    override suspend fun takePicture(): ByteArray? = suspendCancellableCoroutine { c ->
        // 시뮬레이터 등 카메라 불가 시 포토 라이브러리로 fallback
        val canUseCamera = UIImagePickerController.isSourceTypeAvailable(
            UIImagePickerControllerSourceType.UIImagePickerControllerSourceTypeCamera
        )

        val picker = UIImagePickerController().apply {
            sourceType = if (canUseCamera)
                UIImagePickerControllerSourceType.UIImagePickerControllerSourceTypeCamera
            else
                UIImagePickerControllerSourceType.UIImagePickerControllerSourceTypePhotoLibrary
            allowsEditing = false
        }

        val delegate = CameraImage(
            cont = c,
            onDone = { delegateRef = null } // 콜백 종료 후 해제
        )
        delegateRef = delegate
        picker.delegate = delegate

        // 최상단 VC 찾고 메인스레드에서 present
        val presentBlock = {
            val root = UIApplication.sharedApplication.keyWindow?.rootViewController
                ?: UIApplication.sharedApplication.connectedScenes
                    .filterIsInstance<UIWindowScene>()
                    .firstOrNull()?.keyWindow?.rootViewController

            if (root != null) {
                root.presentViewController(picker, animated = true, completion = null)
            } else {
                delegateRef = null
                c.resume(null)
            }
        }
        dispatch_async(dispatch_get_main_queue(), presentBlock)
    }
}

@Composable
actual fun rememberCameraController(): CameraController = remember { IOSCameraController() }