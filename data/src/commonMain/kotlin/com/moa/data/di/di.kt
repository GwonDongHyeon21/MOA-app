package com.moa.data.di

import com.moa.data.network.ApiService
import com.moa.data.repository.SignRepositoryImpl
import com.moa.domain.repository.SignRepository
import org.koin.dsl.module

val dataModule = module {
    single<ApiService> { object : ApiService {} }
    single<SignRepository> { SignRepositoryImpl(get()) }
}