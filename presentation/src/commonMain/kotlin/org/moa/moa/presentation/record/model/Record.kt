package org.moa.moa.presentation.record.model

import com.moa.domain.model.response.RecordResponse
import org.moa.moa.presentation.home.home.model.Emotion

data class Record(
    val date: String,
    val content: String,
    val imageUrl: List<String>?,
    val records: List<RecordResponse>?,
    val emotion: Emotion?,
)