package com.moa.data.repository

import com.moa.data.network.ApiService
import com.moa.domain.repository.RecordRepository

class RecordRepositoryImpl(
    private val apiService: ApiService,
) : RecordRepository {

}