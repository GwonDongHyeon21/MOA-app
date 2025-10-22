package com.moa.domain.usecase.record

import com.moa.domain.repository.RecordRepository

class GetRecord(
    private val recordRepository: RecordRepository,
) {
    suspend operator fun invoke(date: String) = recordRepository.getRecords(date)
}