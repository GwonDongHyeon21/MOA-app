package com.moa.domain.usecase.record

import com.moa.domain.model.request.CreateDiaryRequest
import com.moa.domain.repository.RecordRepository

class CreateDairy(
    private val repository: RecordRepository,
) {
    suspend operator fun invoke(diary: CreateDiaryRequest) = repository.createDiary(diary)
}