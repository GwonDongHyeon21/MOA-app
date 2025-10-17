package com.moa.domain.di

import com.moa.domain.usecase.record.RecordUseCase
import com.moa.domain.usecase.sign.SignUseCase
import com.moa.domain.usecase.record.AddRecord
import com.moa.domain.usecase.record.GetRecord
import com.moa.domain.usecase.sign.GetUserInfo
import com.moa.domain.usecase.sign.GoogleSignUp
import org.koin.dsl.module

val domainModule = module {
    factory { SignUseCase(get(), get()) }
    factory { GetUserInfo(get()) }
    factory { GoogleSignUp(get()) }

    factory { RecordUseCase(get(), get()) }
    factory { GetRecord(get()) }
    factory { AddRecord(get()) }
}