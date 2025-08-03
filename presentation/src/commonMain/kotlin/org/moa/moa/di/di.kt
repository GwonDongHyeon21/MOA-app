package org.moa.moa.di

import org.koin.dsl.module
import org.moa.moa.presentation.user.UserViewModel

val presentationModule = module {
    factory { UserViewModel(get()) }
}