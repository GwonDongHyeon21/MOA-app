package org.moa.moa

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.moa.data.di.dataModule
import com.moa.domain.di.domainModule
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.moa.moa.di.platformModule
import org.moa.moa.platform.haptic.Haptic
import org.moa.moa.di.presentationModule

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        Haptic.initialize(this)
        initKoin()

        setContent {
            MOAApp()
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