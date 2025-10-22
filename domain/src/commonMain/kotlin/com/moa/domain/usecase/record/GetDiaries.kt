package com.moa.domain.usecase.record

import com.moa.domain.repository.RecordRepository

class GetDiaries(
    private val recordRepository: RecordRepository
) {
    suspend operator fun invoke() = recordRepository.getDiaries()
}