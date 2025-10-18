package com.moa.data.repositoryimpl

import com.moa.data.network.ApiService
import com.moa.domain.DummyData
import com.moa.domain.model.request.RecordRequest
import com.moa.domain.model.response.RecordByDateResponse
import com.moa.domain.repository.RecordRepository

class RecordRepositoryImpl(
    private val apiService: ApiService,
) : RecordRepository {

    override suspend fun getRecords(date: String): List<RecordByDateResponse> {
//        val response = apiService.getRecords()
        // 테스트를 위한 더미 데이터
        return DummyData.sampleRecords
    }

    override suspend fun addRecord(record: RecordRequest): String {
//        return apiService.addRecord
        return ""
    }
}