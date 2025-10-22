package org.moa.moa.presentation.home.detail

import com.moa.domain.model.RecordResponse

data class HomeDetailUiState(
    val screenState: HomeDetailScreenState,
    val records: List<RecordResponse>?,
    val record: RecordResponse?,
)

enum class HomeDetailScreenState {
    SUCCESS,
    DELETE_SUCCESS,
    LOADING,
    ERROR
}