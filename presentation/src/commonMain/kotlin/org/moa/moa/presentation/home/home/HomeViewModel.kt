package org.moa.moa.presentation.home.home

import androidx.compose.ui.Alignment
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import moa.presentation.generated.resources.Res
import moa.presentation.generated.resources.home_record1
import moa.presentation.generated.resources.home_record2
import moa.presentation.generated.resources.home_record3
import moa.presentation.generated.resources.home_record4
import moa.presentation.generated.resources.home_record5
import org.moa.moa.presentation.UiState
import org.moa.moa.presentation.home.home.model.ImageInfo
import org.moa.moa.usecaseimpl.UiRecordRepositoryImpl
import org.moa.moa.usecaseimpl.UiTodoRepositoryImpl

class HomeViewModel(
    private val recordRepositoryImpl: UiRecordRepositoryImpl,
    private val todoRepositoryImpl: UiTodoRepositoryImpl,
) : ViewModel() {

    private val recordImages = listOf(
        ImageInfo(Res.drawable.home_record5, Alignment.TopEnd, 150.dp, Offset(-15f, -65f)),
        ImageInfo(Res.drawable.home_record4, Alignment.TopStart, 200.dp, Offset(20f, -110f)),
        ImageInfo(Res.drawable.home_record3, Alignment.Center, 130.dp, Offset(0f, -55f)),
        ImageInfo(Res.drawable.home_record2, Alignment.BottomStart, 180.dp, Offset(20f, 25f)),
        ImageInfo(Res.drawable.home_record1, Alignment.BottomEnd, 200.dp, Offset(-20f, 40f))
    )

    val uiState: StateFlow<HomeUiState> = combine(
        recordRepositoryImpl.todayDiary,
        recordRepositoryImpl.todayRecords
    ) { diary, records ->
        HomeUiState(
            screenState = UiState.SUCCESS,
            diary = diary,
            todayRecords = records,
            recordImages = recordImages,
        )
    }.onStart {
        recordRepositoryImpl.getDiaries()
        recordRepositoryImpl.getRecords()
        todoRepositoryImpl.getTodos()
    }.catch {
        emit(
            HomeUiState(
                screenState = UiState.ERROR,
                diary = null,
                todayRecords = emptyList(),
                recordImages = recordImages,
            )
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = HomeUiState(
            screenState = UiState.LOADING,
            diary = null,
            todayRecords = emptyList(),
            recordImages = recordImages,
        )
    )
}