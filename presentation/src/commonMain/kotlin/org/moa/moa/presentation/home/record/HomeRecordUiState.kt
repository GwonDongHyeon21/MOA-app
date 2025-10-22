package org.moa.moa.presentation.home.record

import com.moa.domain.model.response.Diary
import org.moa.moa.presentation.home.home.model.Emotion

data class HomeRecordUiState(
    val screenState: HomeRecordScreenState,
    val date: String,
    val diary: Diary?,
    val emotion: Emotion?,
    val isLoading: Boolean,
)

enum class HomeRecordScreenState {
    SUCCESS,
    ERROR
}