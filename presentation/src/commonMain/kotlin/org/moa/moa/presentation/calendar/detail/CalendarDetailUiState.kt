package org.moa.moa.presentation.calendar.detail

import com.moa.domain.model.response.Diary
import org.moa.moa.presentation.UiState

data class CalendarDetailUiState(
    val screenState: UiState,
    val date: String,
    val diary: Diary?,
    val diaries: List<Diary>,
)