package org.moa.moa.presentation.sign

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moa.domain.model.User
import com.moa.domain.usecase.SignUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.moa.moa.presentation.UiState

class SignUpViewModel(
    private val signUseCase: SignUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        SignUpUiState(
            userId = "",
            birthDate = "",
            gender = null,
            screenState = UiState.SUCCESS
        )
    )
    val uiState: StateFlow<SignUpUiState> = _uiState

    fun userIdChange(userId: String) {
        _uiState.value = _uiState.value.copy(userId = userId)
    }

    fun birthDateChange(birthDate: String) {
        _uiState.value = _uiState.value.copy(birthDate = birthDate)
    }

    fun genderChange(gender: Gender?) {
        _uiState.value = _uiState.value.copy(gender = gender)
    }

    fun signUp(onSignUp: () -> Unit) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(screenState = UiState.LOADING)
            runCatching {
                signUseCase.signUp(
                    User(
                        _uiState.value.userId,
                        _uiState.value.birthDate,
                        getGender(_uiState.value.gender)
                    )
                )
            }.onSuccess {
                // 회원가입 성공
                onSignUp()
            }.onFailure {
                _uiState.value = _uiState.value.copy(screenState = UiState.ERROR)
                it.printStackTrace()
            }
        }
    }

    private fun getGender(gender: Gender?) = when (gender) {
        Gender.MEN -> 0
        Gender.WOMEN -> 1
        null -> -1
    }
}