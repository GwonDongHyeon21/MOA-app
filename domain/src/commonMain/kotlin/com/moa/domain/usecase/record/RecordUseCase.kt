package com.moa.domain.usecase.record

class RecordUseCase(
    val getDiaries: GetDiaries,
    val getRecords: GetRecords,
    val addTextImageRecord: AddTextImageRecord,
    val addAudioRecord: AddAudioRecord,
)