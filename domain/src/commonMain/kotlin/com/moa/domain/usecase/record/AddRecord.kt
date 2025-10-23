package com.moa.domain.usecase.record

import com.moa.domain.model.request.AddRecordRequest
import com.moa.domain.model.response.AddRecordResponse
import com.moa.domain.repository.RecordRepository

class AddRecord(
    private val recordRepository: RecordRepository
) {
    suspend operator fun invoke(record: AddRecordRequest): AddRecordResponse {
        return recordRepository.addRecord(record)
    }
}