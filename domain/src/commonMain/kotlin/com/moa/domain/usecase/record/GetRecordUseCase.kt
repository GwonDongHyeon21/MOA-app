package com.moa.domain.usecase.record

import com.moa.domain.model.RecordByDateResponse
import com.moa.domain.repository.RecordRepository

class GetRecordUseCase(
    private val recordRepository: RecordRepository,
) {
    suspend operator fun invoke(date: String): Result<List<RecordByDateResponse>> =
        recordRepository.getRecords(date)
}