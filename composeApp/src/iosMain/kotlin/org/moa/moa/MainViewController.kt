package org.moa.moa

import androidx.compose.ui.window.ComposeUIViewController
import com.moa.data.BuildConfig
import com.moa.data.di.dataModule
import com.moa.data.network.ApiService
import com.moa.data.repositoryimpl.SignRepositoryImpl
import com.moa.domain.di.domainModule
import com.moa.domain.usecase.sign.GetUserInfo
import com.moa.domain.usecase.sign.GoogleSignUp
import com.moa.domain.usecase.sign.SignUseCase
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.moa.moa.di.platformModule
import org.moa.moa.di.presentationModule
import org.moa.moa.presentation.sign.SignUpViewModel
import org.moa.moa.presentation.sign.platform.GoogleAuthCodeProvider
import org.moa.moa.presentation.sign.platform.IOSViewControllerHolder

fun MainViewController() = ComposeUIViewController {
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
            IOSViewControllerHolder(),
            BuildConfig.GOOGLE_SERVER_CLIENT_ID
        )
    )
    MOAApp(viewModel)
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