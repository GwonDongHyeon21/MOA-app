package com.moa.domain.usecase.record

class RecordUseCase(
    val getDiaries: GetDiaries,
    val createDiary: CreateDairy,
    val updateDiary: UpdateDiary,
    val getRecords: GetRecords,
    val addRecord: AddRecord,
)