package org.moa.moa.presentation.home.home

import androidx.compose.ui.Alignment
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import moa.presentation.generated.resources.Res
import moa.presentation.generated.resources.home_record1
import moa.presentation.generated.resources.home_record2
import moa.presentation.generated.resources.home_record3
import moa.presentation.generated.resources.home_record4
import moa.presentation.generated.resources.home_record5
import org.moa.moa.presentation.UiState
import org.moa.moa.presentation.home.home.model.ImageInfo
import org.moa.moa.repository.UiRecordRepositoryImpl

class HomeViewModel(
    private val repo: UiRecordRepositoryImpl,
) : ViewModel() {

    private val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date

    private val recordImages = listOf(
        ImageInfo(Res.drawable.home_record5, Alignment.TopEnd, 150.dp, Offset(-15f, -65f)),
        ImageInfo(Res.drawable.home_record4, Alignment.TopStart, 200.dp, Offset(20f, -110f)),
        ImageInfo(Res.drawable.home_record3, Alignment.Center, 130.dp, Offset(0f, -55f)),
        ImageInfo(Res.drawable.home_record2, Alignment.BottomStart, 180.dp, Offset(20f, 25f)),
        ImageInfo(Res.drawable.home_record1, Alignment.BottomEnd, 200.dp, Offset(-20f, 40f))
    )

    private val _uiState = MutableStateFlow(
        HomeUiState(
            screenState = UiState.LOADING,
            records = emptyList(),
            todayRecord = null,
            recordImages = recordImages
        )
    )
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repo.getRecords(today.toString())
            repo.todayRecord.collect { record ->
                _uiState.value = _uiState.value.copy(
                    screenState = UiState.SUCCESS,
                    records = record?.records.orEmpty(),
                    todayRecord = record
                )
            }
        }
    }
}