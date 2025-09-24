package org.moa.moa.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.suspendCancellableCoroutine
import java.io.File
import java.io.FileInputStream

private class AndroidCameraController(
    private val context: Context,
    private val takePictureInternal: suspend (Uri) -> Boolean,
) : CameraController {

    override suspend fun takePicture(): ByteArray? {
        // cache/images/ 에 임시 파일 생성
        val dir = File(context.cacheDir, "images").apply { mkdirs() }
        val dest = File(dir, "capture_${System.currentTimeMillis()}.jpg")

        val uri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            dest
        )

        val ok = takePictureInternal(uri)
        if (!ok) return null

        // 파일에서 바이트 읽기
        val bytes = FileInputStream(dest).use { it.readBytes() }
        return bytes
    }
}

@Composable
actual fun rememberCameraController(): CameraController {
    val context = LocalContext.current

    // 1) 카메라 권한 요청 런처
    var permCont by remember { mutableStateOf<CancellableContinuation<Boolean>?>(null) }
    val requestPermission = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        permCont?.resume(granted) { }
        permCont = null
    }

    // 2) 사진 촬영 런처
    var takeCont by remember { mutableStateOf<CancellableContinuation<Boolean>?>(null) }
    val takePictureLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { ok ->
        takeCont?.resume(ok) { }
        takeCont = null
    }

    suspend fun ensureCameraPermission(): Boolean {
        if (ContextCompat.checkSelfPermission(
                context, Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) return true

        return suspendCancellableCoroutine { c ->
            permCont = c
            requestPermission.launch(Manifest.permission.CAMERA)
        }
    }

    val takePictureInternal: suspend (Uri) -> Boolean = { uri ->
        if (!ensureCameraPermission()) {
            false
        } else {
            suspendCancellableCoroutine { cont ->
                takeCont = cont
                takePictureLauncher.launch(uri)
            }
        }
    }

    return remember {
        AndroidCameraController(context, takePictureInternal)
    }
}