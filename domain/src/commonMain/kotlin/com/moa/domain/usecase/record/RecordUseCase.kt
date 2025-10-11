package com.moa.domain.usecase.record

class RecordUseCase(
    val getRecords: GetRecordUseCase,
    val addRecord: AddRecordUseCase,
)