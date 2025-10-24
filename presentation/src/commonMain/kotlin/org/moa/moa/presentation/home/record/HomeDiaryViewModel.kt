package org.moa.moa.presentation.home.record

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moa.domain.model.request.CreateDiaryRequest
import com.moa.domain.model.request.UpdateDiaryRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.moa.moa.presentation.home.home.model.Emotion
import org.moa.moa.repository.UiRecordRepositoryImpl

class HomeDiaryViewModel(
    private val repo: UiRecordRepositoryImpl,
) : ViewModel() {

    private val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date

    private val _uiState = MutableStateFlow(
        HomeDiaryUiState(
            screenState = HomeDiaryScreenState.SUCCESS,
            date = today.toString(),
            diary = null,
            diaryText = "",
            emotion = null,
            isLoading = true,
            isEditMode = false
        )
    )
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repo.todayDiary.collect { diary ->
                diary?.let {
                    _uiState.value = _uiState.value.copy(
                        diary = it,
                        diaryText = it.content,
                        isLoading = false
                    )
                } ?: run {
                    createDiary()
                }
            }
        }
    }

    private fun createDiary() {
        viewModelScope.launch {
            runCatching {
                repo.createDiary(
                    CreateDiaryRequest(
                        date = today.toString(),
                        persona = 0
                    )
                )
            }.onFailure {
                _uiState.value = _uiState.value.copy(screenState = HomeDiaryScreenState.ERROR)
            }
        }
    }

    fun selectEmotion(emotion: Emotion?) {
        _uiState.value = _uiState.value.copy(emotion = emotion)
    }

    fun changeMode() {
        _uiState.value = _uiState.value.copy(isEditMode = !_uiState.value.isEditMode)
    }

    fun changeDiaryText(text: String) {
        _uiState.value = _uiState.value.copy(diaryText = text)
    }

    fun updateDiary() {
        viewModelScope.launch {
            runCatching {
                _uiState.value.diary?.let { diary->
                    repo.updateDiary(
                        UpdateDiaryRequest(
                            id = diary.id,
                            content = _uiState.value.diaryText,
                            emotion = _uiState.value.emotion?.label
                        )
                    )
                }
            }.onFailure {
                _uiState.value = _uiState.value.copy(screenState = HomeDiaryScreenState.ERROR)
            }
        }
    }
}