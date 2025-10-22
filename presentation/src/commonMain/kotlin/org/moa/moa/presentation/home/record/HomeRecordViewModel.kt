package org.moa.moa.presentation.home.record

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.moa.moa.presentation.home.home.model.Emotion
import org.moa.moa.repository.UiRecordRepositoryImpl

class HomeRecordViewModel(
    private val repo: UiRecordRepositoryImpl,
) : ViewModel() {

    private val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date

    private val _uiState = MutableStateFlow(
        HomeRecordUiState(
            screenState = HomeRecordScreenState.SUCCESS,
            date = today.toString(),
            diary = null,
            emotion = null,
            isLoading = true
        )
    )
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            delay(4_000)
            repo.todayDiary.collect { diary ->
                _uiState.value = _uiState.value.copy(
                    diary = diary,
                    isLoading = false
                )
            }
        }
    }

    fun selectEmotion(emotion: Emotion?) {
        _uiState.value = _uiState.value.copy(emotion = emotion)
    }

    fun decideEmotion() {
        viewModelScope.launch {
//            repo.decideEmotion(_uiState.value.emotion?.label)
        }
    }
}