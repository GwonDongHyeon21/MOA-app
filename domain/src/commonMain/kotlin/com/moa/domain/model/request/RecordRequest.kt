package com.moa.domain.model.request

data class RecordRequest(
    val date: String,
    val content: String,
    val imageBytes: ByteArray?,
)