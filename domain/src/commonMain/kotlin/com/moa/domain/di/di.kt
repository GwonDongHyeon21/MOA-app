package com.moa.domain.di

import com.moa.domain.usecase.GetUserUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { GetUserUseCase(get()) }
}