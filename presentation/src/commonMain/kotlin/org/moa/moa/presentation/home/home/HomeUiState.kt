package org.moa.moa.presentation.home.home

import com.moa.domain.model.response.Diary
import com.moa.domain.model.response.RecordItem
import org.moa.moa.presentation.UiState
import org.moa.moa.presentation.home.home.model.ImageInfo

data class HomeUiState(
    val screenState: UiState,
    val diary: Diary?,
    val todayRecords:List<RecordItem>,
    val recordImages: List<ImageInfo>,
)