package com.moa.domain.repository

import com.moa.domain.model.request.RecordRequest
import com.moa.domain.model.response.RecordByDateResponse

interface RecordRepository {
    suspend fun getRecords(date: String): Result<List<RecordByDateResponse>>
    suspend fun addRecord(record: RecordRequest): String
}