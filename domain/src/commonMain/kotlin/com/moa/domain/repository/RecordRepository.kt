package com.moa.domain.repository

import com.moa.domain.model.RecordResponse
import com.moa.domain.model.ResponseMessage

interface RecordRepository {
    suspend fun getRecords(date: String): List<RecordResponse>
    suspend fun addRecord(record: RecordResponse): ResponseMessage
}