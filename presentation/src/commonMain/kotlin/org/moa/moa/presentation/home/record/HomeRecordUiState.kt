package org.moa.moa.presentation.home.record

import org.moa.moa.presentation.home.home.model.Emotion
import org.moa.moa.presentation.record.model.Record

data class HomeRecordUiState(
    val screenState: HomeRecordScreenState,
    val date: String,
    val record: Record?,
    val emotion: Emotion?,
    val isLoading: Boolean,
)

enum class HomeRecordScreenState {
    SUCCESS,
    ERROR
}