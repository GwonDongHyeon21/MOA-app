package com.moa.domain.repository

import com.moa.domain.model.RecordByDateResponse
import com.moa.domain.model.RecordRequest
import com.moa.domain.model.ResponseMessage

interface RecordRepository {
    suspend fun getRecords(date: String): Result<List<RecordByDateResponse>>
    suspend fun addRecord(record: RecordRequest): ResponseMessage
}