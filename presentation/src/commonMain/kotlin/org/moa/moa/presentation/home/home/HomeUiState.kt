package org.moa.moa.presentation.home.home

import com.moa.domain.model.RecordResponse
import org.moa.moa.presentation.UiState
import org.moa.moa.presentation.home.home.model.ImageInfo
import org.moa.moa.presentation.record.model.Record

data class HomeUiState(
    val screenState: UiState,
    val records: List<RecordResponse>,
    val todayRecord: Record?,
    val recordImages: List<ImageInfo>,
)