package org.moa.moa.di

import org.koin.dsl.module
import org.moa.moa.presentation.calendar.calendar.CalendarViewModel
import org.moa.moa.presentation.calendar.detail.CalendarDetailViewModel
import org.moa.moa.presentation.home.detail.HomeDetailViewModel
import org.moa.moa.presentation.home.home.HomeViewModel
import org.moa.moa.presentation.home.record.HomeDiaryViewModel
import org.moa.moa.presentation.record.recorder.RecorderViewModel
import org.moa.moa.presentation.record.textimage.RecordViewModel
import org.moa.moa.presentation.todo.TodoViewModel
import org.moa.moa.presentation.user.UserViewModel
import org.moa.moa.usecaseimpl.UiRecordRepositoryImpl
import org.moa.moa.usecaseimpl.UiTodoRepositoryImpl
import org.moa.moa.usecaseimpl.UiUserRepositoryImpl

val presentationModule = module {
    single { UiRecordRepositoryImpl(get()) }
    single { UiTodoRepositoryImpl(get()) }
    single { UiUserRepositoryImpl() }

    factory { HomeViewModel(get(), get()) }
    factory { HomeDiaryViewModel(get()) }
    factory { HomeDetailViewModel(get()) }
    factory { RecordViewModel(get()) }
    factory { RecorderViewModel(get()) }
    factory { CalendarViewModel(get()) }
    factory { CalendarDetailViewModel(get()) }
    factory { TodoViewModel(get()) }
    factory { UserViewModel(get()) }
}