package com.moa.domain.usecase.record

import com.moa.domain.model.request.UpdateDiaryRequest
import com.moa.domain.repository.RecordRepository

class UpdateDiary(
    private val repository: RecordRepository,
) {
    suspend operator fun invoke(diary: UpdateDiaryRequest) = repository.updateDiary(diary)
}