package com.moa.data.di

import com.moa.data.repository.UserRepositoryImpl
import com.moa.domain.repository.UserRepository
import org.koin.dsl.module

val dataModule = module {
    single<UserRepository> { UserRepositoryImpl() }
}
