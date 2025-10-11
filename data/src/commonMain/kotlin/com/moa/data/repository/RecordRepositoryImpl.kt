package com.moa.data.repository

import com.moa.data.network.ApiService
import com.moa.domain.DummyData
import com.moa.domain.model.RecordResponse
import com.moa.domain.model.ResponseMessage
import com.moa.domain.repository.RecordRepository

class RecordRepositoryImpl(
    private val apiService: ApiService,
) : RecordRepository {

    override suspend fun getRecords(date: String): List<RecordResponse> {
//        val response = apiService.getRecords()
        // 테스트를 위한 더미 데이터
        return DummyData.sampleRecords
    }

    override suspend fun addRecord(record: RecordResponse): ResponseMessage {
//        return apiService.addRecord
        return ResponseMessage("")
    }
}