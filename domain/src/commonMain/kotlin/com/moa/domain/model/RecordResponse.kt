package com.moa.domain.model

data class RecordResponse(
    val date: String,
    val content: String,
    val imageUrl: String? = null,
    val emotion: String? = null,
)