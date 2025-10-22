package com.moa.domain.repository

import com.moa.domain.model.request.AddAudioRecordRequest
import com.moa.domain.model.request.AddTextImageRecordRequest
import com.moa.domain.model.response.AddRecordResponse
import com.moa.domain.model.response.GetDiariesResponse
import com.moa.domain.model.response.GetRecordsResponse

interface RecordRepository {
    suspend fun getDiaries(): GetDiariesResponse
    suspend fun getRecords(date: String): GetRecordsResponse
    suspend fun addTextImageRecord(record: AddTextImageRecordRequest): AddRecordResponse
    suspend fun addAudioRecord(record: AddAudioRecordRequest): String
}