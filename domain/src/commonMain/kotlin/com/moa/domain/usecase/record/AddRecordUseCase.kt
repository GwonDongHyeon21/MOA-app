package com.moa.domain.usecase.record

import com.moa.domain.model.RecordRequest
import com.moa.domain.model.ResponseMessage
import com.moa.domain.repository.RecordRepository

class AddRecordUseCase(
    private val recordRepository: RecordRepository
) {
    suspend operator fun invoke(record: RecordRequest): ResponseMessage {
        return recordRepository.addRecord(record)
    }
}