package com.moa.data.di

import com.moa.data.network.ApiService
import com.moa.data.repositoryimpl.RecordRepositoryImpl
import com.moa.data.repositoryimpl.SignRepositoryImpl
import com.moa.data.repositoryimpl.TodoRepositoryImpl
import com.moa.domain.repository.RecordRepository
import com.moa.domain.repository.SignRepository
import com.moa.domain.repository.TodoRepository
import org.koin.dsl.module

val dataModule = module {
    single<ApiService> { ApiService() }
    single<SignRepository> { SignRepositoryImpl(get()) }
    single<RecordRepository> { RecordRepositoryImpl(get()) }
    single<TodoRepository> { TodoRepositoryImpl(get()) }
}