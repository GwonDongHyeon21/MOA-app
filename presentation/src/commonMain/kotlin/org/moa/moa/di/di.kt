package org.moa.moa.di

import org.koin.dsl.module
import org.moa.moa.presentation.sign.SignUpViewModel

val presentationModule = module {
    factory { SignUpViewModel(get()) }
}