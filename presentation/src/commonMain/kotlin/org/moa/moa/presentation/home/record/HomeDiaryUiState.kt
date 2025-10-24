package org.moa.moa.presentation.home.record

import com.moa.domain.model.response.Diary
import org.moa.moa.presentation.home.home.model.Emotion

data class HomeDiaryUiState(
    val screenState: HomeDiaryScreenState,
    val date: String,
    val diary: Diary?,
    val emotion: Emotion?,
    val isLoading: Boolean,
    val isEditMode: Boolean,
)

enum class HomeDiaryScreenState {
    SUCCESS,
    ERROR
}