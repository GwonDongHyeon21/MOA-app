package org.moa.moa.di

import org.koin.dsl.module
import org.moa.moa.presentation.calendar.CalendarViewModel
import org.moa.moa.presentation.record.textimage.RecordViewModel
import org.moa.moa.presentation.record.recorder.RecorderViewModel
import org.moa.moa.presentation.sign.SignUpViewModel

val presentationModule = module {
    factory { SignUpViewModel(get()) }
    factory { RecordViewModel(get()) }
    factory { RecorderViewModel(get()) }
    factory { CalendarViewModel(get()) }
}