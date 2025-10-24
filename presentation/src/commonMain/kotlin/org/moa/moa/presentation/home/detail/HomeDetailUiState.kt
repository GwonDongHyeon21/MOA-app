package org.moa.moa.presentation.home.detail

import com.moa.domain.model.response.RecordItem

data class HomeDetailUiState(
    val screenState: HomeDetailScreenState,
    val records: List<RecordItem>,
    val record: RecordItem?,
)

enum class HomeDetailScreenState {
    SUCCESS,
    DELETE_SUCCESS,
    LOADING,
    ERROR
}