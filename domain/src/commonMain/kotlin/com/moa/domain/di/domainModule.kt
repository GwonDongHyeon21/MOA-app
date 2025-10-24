package com.moa.domain.di

import com.moa.domain.usecase.record.AddRecord
import com.moa.domain.usecase.record.GetDiaries
import com.moa.domain.usecase.record.GetRecords
import com.moa.domain.usecase.record.RecordUseCase
import com.moa.domain.usecase.sign.GetUserInfo
import com.moa.domain.usecase.sign.GoogleSignUp
import com.moa.domain.usecase.sign.SignUseCase
import com.moa.domain.usecase.todo.AddTodo
import com.moa.domain.usecase.todo.DeleteTodo
import com.moa.domain.usecase.todo.GetTodos
import com.moa.domain.usecase.todo.TodoUseCase
import com.moa.domain.usecase.todo.UpdateTodo
import org.koin.dsl.module

val domainModule = module {
    factory { SignUseCase(get(), get()) }
    factory { GetUserInfo(get()) }
    factory { GoogleSignUp(get()) }

    factory { RecordUseCase(get(), get(), get()) }
    factory { GetDiaries(get()) }
    factory { GetRecords(get()) }
    factory { AddRecord(get()) }

    factory { TodoUseCase(get(), get(), get(), get()) }
    factory { GetTodos(get()) }
    factory { AddTodo(get()) }
    factory { UpdateTodo(get()) }
    factory { DeleteTodo(get()) }
}