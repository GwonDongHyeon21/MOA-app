package com.moa.domain.di

import com.moa.domain.usecase.record.RecordUseCase
import com.moa.domain.usecase.SignUseCase
import com.moa.domain.usecase.record.AddRecordUseCase
import com.moa.domain.usecase.record.GetRecordUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { SignUseCase(get()) }

    factory { RecordUseCase(get(), get()) }
    factory { GetRecordUseCase(get()) }
    factory { AddRecordUseCase(get()) }
}