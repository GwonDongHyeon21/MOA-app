package com.moa.domain.di

import com.moa.domain.usecase.SignUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { SignUseCase(get()) }
}