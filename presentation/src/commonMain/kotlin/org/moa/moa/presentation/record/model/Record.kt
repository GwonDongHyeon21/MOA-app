package org.moa.moa.presentation.record.model

import org.moa.moa.presentation.home.model.Emotion

data class Record(
    val content: String,
    val emotion: Emotion,
)