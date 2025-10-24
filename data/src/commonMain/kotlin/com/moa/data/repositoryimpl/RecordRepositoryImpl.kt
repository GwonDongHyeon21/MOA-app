package com.moa.data.repositoryimpl

import com.moa.data.network.ApiService
import com.moa.domain.model.request.AddRecordRequest
import com.moa.domain.model.request.CreateDiaryRequest
import com.moa.domain.model.response.AddRecordResponse
import com.moa.domain.model.response.CreateDiaryResponse
import com.moa.domain.model.response.GetDiariesResponse
import com.moa.domain.model.response.GetRecordsResponse
import com.moa.domain.repository.RecordRepository

class RecordRepositoryImpl(
    private val apiService: ApiService,
) : RecordRepository {

    override suspend fun getDiaries(): GetDiariesResponse {
        return apiService.getDiaries()
    }

    override suspend fun createDiary(diary: CreateDiaryRequest): CreateDiaryResponse {
        return apiService.createDiary(diary)
    }

    override suspend fun getRecords(): GetRecordsResponse {
        return apiService.getRecords()
    }

    override suspend fun addRecord(record: AddRecordRequest): AddRecordResponse {
        return apiService.addRecord(record)
    }
}