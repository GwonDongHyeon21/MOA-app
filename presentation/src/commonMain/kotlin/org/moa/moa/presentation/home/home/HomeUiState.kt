package org.moa.moa.presentation.home.home

import com.moa.domain.model.response.Diary
import org.moa.moa.presentation.UiState
import org.moa.moa.presentation.home.home.model.ImageInfo

data class HomeUiState(
    val screenState: UiState,
    val diary: Diary?,
    val recordImages: List<ImageInfo>,
)