package com.moa.data.repository

import com.moa.data.network.ApiService
import com.moa.domain.DummyData
import com.moa.domain.model.RecordByDateResponse
import com.moa.domain.model.RecordRequest
import com.moa.domain.model.RecordResponse
import com.moa.domain.model.ResponseMessage
import com.moa.domain.repository.RecordRepository

class RecordRepositoryImpl(
    private val apiService: ApiService,
) : RecordRepository {

    override suspend fun getRecords(date: String): Result<List<RecordByDateResponse>> {
//        val response = apiService.getRecords()
        // 테스트를 위한 더미 데이터
        return runCatching { DummyData.sampleRecords }
    }

    override suspend fun addRecord(record: RecordRequest): ResponseMessage {
//        return apiService.addRecord
        return ResponseMessage("")
    }
}