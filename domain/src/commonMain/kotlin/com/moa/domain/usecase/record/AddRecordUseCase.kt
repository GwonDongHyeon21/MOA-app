package com.moa.domain.usecase.record

import com.moa.domain.model.RecordResponse
import com.moa.domain.repository.RecordRepository

class AddRecordUseCase(
    private val recordRepository: RecordRepository
) {
    suspend operator fun invoke(record: RecordResponse) {
        recordRepository.addRecord(record)
    }
}