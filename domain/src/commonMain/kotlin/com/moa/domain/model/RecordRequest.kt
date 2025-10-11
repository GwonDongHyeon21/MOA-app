package com.moa.domain.model

data class RecordRequest(
    val date: String,
    val content: String,
    val imageBytes: ByteArray?,
)