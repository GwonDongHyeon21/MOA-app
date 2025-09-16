package org.moa.moa.di

import org.koin.dsl.module
import org.moa.moa.presentation.record.text.RecordTextViewModel
import org.moa.moa.presentation.sign.SignUpViewModel

val presentationModule = module {
    factory { SignUpViewModel(get()) }
    factory { RecordTextViewModel(get()) }
}