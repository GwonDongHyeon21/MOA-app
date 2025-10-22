package com.moa.domain.usecase.record

import com.moa.domain.model.request.AddAudioRecordRequest
import com.moa.domain.repository.RecordRepository

class AddAudioRecord(
    private val recordRepository: RecordRepository,
) {
    suspend operator fun invoke(record: AddAudioRecordRequest): String {
        return recordRepository.addAudioRecord(record)
    }
}