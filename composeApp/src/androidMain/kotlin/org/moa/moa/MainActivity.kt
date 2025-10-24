package org.moa.moa

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.moa.data.BuildConfig
import com.moa.data.di.dataModule
import com.moa.data.network.ApiClient
import com.moa.data.network.ApiService
import com.moa.data.repositoryimpl.SignRepositoryImpl
import com.moa.domain.di.domainModule
import com.moa.domain.usecase.sign.GetUserInfo
import com.moa.domain.usecase.sign.GoogleSignUp
import com.moa.domain.usecase.sign.SignUseCase
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.moa.moa.di.platformModule
import org.moa.moa.platform.haptic.Haptic
import org.moa.moa.di.presentationModule
import org.moa.moa.presentation.sign.platform.GoogleAuthActivityHelper
import org.moa.moa.presentation.sign.platform.GoogleAuthCodeProvider
import org.moa.moa.presentation.sign.SignUpViewModel

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        Haptic.initialize(this)
        initKoin()

        val viewModel = SignUpViewModel(
            signUseCase = SignUseCase(
                getUserInfo = GetUserInfo(
                    SignRepositoryImpl(ApiService())
                ),
                googleSignUp = GoogleSignUp(
                    SignRepositoryImpl(ApiService())
                )
            ),
            googleAuthCodeProvider = GoogleAuthCodeProvider(
                GoogleAuthActivityHelper(this),
                BuildConfig.GOOGLE_SERVER_CLIENT_ID
            )
        )

        setContent {
            MOAApp(viewModel = viewModel)
        }
    }
}

fun initKoin() {
    startKoin {
        printLogger(Level.DEBUG)
        modules(
            dataModule,
            domainModule,
            presentationModule,
            platformModule
        )
    }
}