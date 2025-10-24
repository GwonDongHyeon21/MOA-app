package org.moa.moa.presentation.sign.platform

import platform.UIKit.UIViewController
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class IOSViewControllerHolder(val presenter: UIViewController = UIViewController())

actual class GoogleAuthCodeProvider(
    private val holder: IOSViewControllerHolder,
    private val serverClientId: String,
) {
    actual suspend fun fetchServerAuthCode(): String = ""
//        suspendCoroutine { cont ->
//            signInWithGoogle(
//                serverClientId,
//                holder.presenter
//            ) { code, error ->
//                when {
//                    error != null -> cont.resumeWithException(Throwable(error))
//                    code != null -> cont.resume(code)
//                    else -> cont.resumeWithException(Throwable("Unknown error"))
//                }
//            }
//        }
}
