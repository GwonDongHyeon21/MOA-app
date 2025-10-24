package com.moa.domain.repository

import com.moa.domain.model.request.AddRecordRequest
import com.moa.domain.model.request.CreateDiaryRequest
import com.moa.domain.model.request.UpdateDiaryRequest
import com.moa.domain.model.response.AddRecordResponse
import com.moa.domain.model.response.CreateDiaryResponse
import com.moa.domain.model.response.Diary
import com.moa.domain.model.response.GetDiariesResponse
import com.moa.domain.model.response.GetRecordsResponse
import com.moa.domain.model.response.ResponseMessage

interface RecordRepository {
    suspend fun getDiaries(): GetDiariesResponse
    suspend fun createDiary(diary: CreateDiaryRequest): CreateDiaryResponse
    suspend fun updateDiary(diary: UpdateDiaryRequest): ResponseMessage
    suspend fun getRecords(): GetRecordsResponse
    suspend fun addRecord(record: AddRecordRequest): AddRecordResponse
}