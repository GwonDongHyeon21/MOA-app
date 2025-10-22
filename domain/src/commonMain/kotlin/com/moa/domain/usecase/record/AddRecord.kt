package com.moa.domain.usecase.record

import com.moa.domain.model.request.RecordRequest
import com.moa.domain.repository.RecordRepository

class AddRecord(
    private val recordRepository: RecordRepository
) {
    suspend operator fun invoke(record: RecordRequest): String {
        return recordRepository.addRecord(record)
    }
}