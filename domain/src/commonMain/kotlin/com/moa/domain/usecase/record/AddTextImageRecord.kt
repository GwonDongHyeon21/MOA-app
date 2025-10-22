package com.moa.domain.usecase.record

import com.moa.domain.model.request.AddTextImageRecordRequest
import com.moa.domain.model.response.AddRecordResponse
import com.moa.domain.repository.RecordRepository

class AddTextImageRecord(
    private val recordRepository: RecordRepository
) {
    suspend operator fun invoke(record: AddTextImageRecordRequest): AddRecordResponse {
        return recordRepository.addTextImageRecord(record)
    }
}