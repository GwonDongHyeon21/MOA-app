package com.moa.domain.model

data class RecordByDateResponse(
    val date: String,
    val content: String,
    val imageUrl: List<String>? = null,
    val records: List<RecordResponse>? = null,
    val emotion: String? = null,
)

data class RecordResponse(
    val date: String,
    val content: String,
    val imageUrl: String? = null,
)