package com.moa.domain.model.request

data class AddRecordRequest(
    val content: String?,
    val imageBytes: ByteArray?,
    val audioBytes: ByteArray?,
)