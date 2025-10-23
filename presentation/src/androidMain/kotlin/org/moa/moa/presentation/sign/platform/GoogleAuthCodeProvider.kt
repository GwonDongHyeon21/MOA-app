package org.moa.moa.presentation.sign.platform

import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import kotlinx.coroutines.suspendCancellableCoroutine

class GoogleAuthActivityHelper(private val activity: ComponentActivity) {
    private var pending: ((Result<String>) -> Unit)? = null

    private val launcher =
        activity.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val deliver = pending.also { pending = null }
            try {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                val account: GoogleSignInAccount = task.getResult(ApiException::class.java)
                val code = account.serverAuthCode ?: ""
                deliver?.invoke(Result.success(code))
            } catch (e: Exception) {
                deliver?.invoke(Result.failure(e))
            }
        }

    suspend fun signInAndGetCode(serverClientId: String): String =
        suspendCancellableCoroutine { cont ->
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail().requestServerAuthCode(serverClientId, true).build()
            val client = GoogleSignIn.getClient(
                activity,
                gso
            )
            pending = { res -> cont.resumeWith(res) }
            launcher.launch(client.signInIntent)
            cont.invokeOnCancellation { pending = null }
        }
}

actual class GoogleAuthCodeProvider(
    private val helper: GoogleAuthActivityHelper,
    private val serverClientId: String,
) {
    actual suspend fun fetchServerAuthCode(): String = helper.signInAndGetCode(serverClientId)
}