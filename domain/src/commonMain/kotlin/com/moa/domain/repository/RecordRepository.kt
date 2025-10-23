package com.moa.domain.repository

import com.moa.domain.model.request.AddRecordRequest
import com.moa.domain.model.response.AddRecordResponse
import com.moa.domain.model.response.GetDiariesResponse
import com.moa.domain.model.response.GetRecordsResponse

interface RecordRepository {
    suspend fun getDiaries(): GetDiariesResponse
    suspend fun getRecords(): GetRecordsResponse
    suspend fun addRecord(record: AddRecordRequest): AddRecordResponse
}