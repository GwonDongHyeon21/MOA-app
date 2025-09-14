package org.moa.moa.di

import org.koin.dsl.module
import org.moa.moa.IOSProvider
import org.moa.moa.platform.PlatformProvider

val platformModule = module {
    single<PlatformProvider> { IOSProvider() }
}