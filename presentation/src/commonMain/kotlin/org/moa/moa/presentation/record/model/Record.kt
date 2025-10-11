package org.moa.moa.presentation.record.model

import org.moa.moa.presentation.home.home.model.Emotion

data class Record(
    val date: String,
    val content: String,
    val imageUrl: String?,
    val emotion: Emotion?,
)