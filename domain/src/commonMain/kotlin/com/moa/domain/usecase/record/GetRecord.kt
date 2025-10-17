package com.moa.domain.usecase.record

import com.moa.domain.model.response.RecordByDateResponse
import com.moa.domain.repository.RecordRepository

class GetRecord(
    private val recordRepository: RecordRepository,
) {
    suspend operator fun invoke(date: String): Result<List<RecordByDateResponse>> =
        recordRepository.getRecords(date)
}