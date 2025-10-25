package org.moa.moa.presentation.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.moa.moa.presentation.UiState
import org.moa.moa.presentation.ui.theme.Strings
import org.moa.moa.presentation.util.DummyData
import org.moa.moa.usecaseimpl.UiUserRepositoryImpl

class UserViewModel(
    private val uiUserRepositoryImpl: UiUserRepositoryImpl,
) : ViewModel() {

    private val settings = listOf(Strings.edit_user, Strings.set_template, Strings.setting)

    private val _uiState = MutableStateFlow(
        UserUiState(
            personas = emptyList(),
            settings = settings,
            screenState = UiState.LOADING
        )
    )
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
//            uiUserRepositoryImpl.getUser()
//            uiUserRepositoryImpl.getPersona()
            _uiState.value = _uiState.value.copy(
                personas = DummyData.samplePersona,
                screenState = UiState.SUCCESS
            )
        }
    }
}