package org.moa.moa.di

import org.koin.dsl.module
import org.moa.moa.AndroidProvider
import org.moa.moa.platform.PlatformProvider

val platformModule = module {
    single<PlatformProvider> { AndroidProvider() }
}