package org.moa.moa

import androidx.compose.ui.window.ComposeUIViewController
import com.moa.data.di.dataModule
import com.moa.domain.di.domainModule
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.moa.moa.di.platformModule
import org.moa.moa.di.presentationModule

fun MainViewController() = ComposeUIViewController {
    initKoin()
    MOAApp()
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