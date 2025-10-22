package com.moa.data.repositoryimpl

import com.moa.data.network.ApiService
import com.moa.domain.model.request.AddAudioRecordRequest
import com.moa.domain.model.request.AddTextImageRecordRequest
import com.moa.domain.model.response.AddRecordResponse
import com.moa.domain.model.response.GetDiariesResponse
import com.moa.domain.model.response.GetRecordsResponse
import com.moa.domain.repository.RecordRepository

class RecordRepositoryImpl(
    private val apiService: ApiService,
) : RecordRepository {

    override suspend fun getDiaries(): GetDiariesResponse {
        return apiService.getDiaries()
    }

    override suspend fun getRecords(date: String): GetRecordsResponse {
        return apiService.getRecords()
    }

    override suspend fun addTextImageRecord(record: AddTextImageRecordRequest): AddRecordResponse {
        return apiService.addTextImageRecord(record)
    }

    override suspend fun addAudioRecord(record: AddAudioRecordRequest): String {
        return apiService.addAudioRecord(record)
    }
}