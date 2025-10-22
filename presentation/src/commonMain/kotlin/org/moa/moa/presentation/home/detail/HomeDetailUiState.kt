package org.moa.moa.presentation.home.detail

import com.moa.domain.model.response.DiaryRecord

data class HomeDetailUiState(
    val screenState: HomeDetailScreenState,
    val records: List<DiaryRecord>?,
    val record: DiaryRecord?,
)

enum class HomeDetailScreenState {
    SUCCESS,
    DELETE_SUCCESS,
    LOADING,
    ERROR
}