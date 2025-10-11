package com.moa.domain.usecase.record

import com.moa.domain.model.RecordResponse
import com.moa.domain.repository.RecordRepository

class GetRecordUseCase(
    private val recordRepository: RecordRepository,
) {
    suspend operator fun invoke(date: String): List<RecordResponse> = recordRepository.getRecords(date)
}