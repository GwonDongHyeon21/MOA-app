package org.moa.moa.presentation.sign

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moa.domain.model.request.GetUserInfoRequest
import com.moa.domain.model.request.UserRequest
import com.moa.domain.model.response.SignInResponse
import com.moa.domain.usecase.sign.SignUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.moa.moa.presentation.UiState
import org.moa.moa.presentation.sign.platform.GoogleAuthCodeProvider
import org.moa.moa.util.TokenUtils

class SignUpViewModel(
    private val googleAuthCodeProvider: GoogleAuthCodeProvider,
    private val signUseCase: SignUseCase,
) : ViewModel() {

    private val _onBoardingState = MutableStateFlow(OnBoardingScreenState.ONBOARDING)
    val onBoardingState = _onBoardingState.asStateFlow()

    private val _uiState = MutableStateFlow(
        SignUpUiState(
            userId = "",
            birthDate = "",
            gender = null,
            screenState = UiState.DEFAULT,
        )
    )
    val uiState = _uiState.asStateFlow()

    private var userInfo = MutableStateFlow<SignInResponse?>(null)

    fun userIdChange(userId: String) {
        _uiState.value = _uiState.value.copy(userId = userId)
    }

    fun birthDateChange(birthDate: String) {
        _uiState.value = _uiState.value.copy(birthDate = birthDate)
    }

    fun genderChange(gender: Gender?) {
        _uiState.value = _uiState.value.copy(gender = gender)
    }

    fun setupOnBoarding() {
        _onBoardingState.value = OnBoardingScreenState.ONBOARDING
    }

    fun googleLogin() {
        viewModelScope.launch {
            _onBoardingState.value = OnBoardingScreenState.LOADING
            runCatching {
                googleAuthCodeProvider.fetchServerAuthCode()
            }.onSuccess { token ->
                getUserInfo(token)
            }.onFailure {
                _onBoardingState.value = OnBoardingScreenState.ERROR
            }
        }
    }

    private fun getUserInfo(token: String) {
        val request = GetUserInfoRequest(token)
        viewModelScope.launch {
            runCatching {
                signUseCase.getUserInfo(request)
            }.onSuccess { response ->
                userInfo.value = response

                when (response.status) {
                    "login" -> {
                        if (TokenUtils.saveAccessToken(response.accessToken) &&
                            TokenUtils.saveRefreshToken(response.refreshToken)
                        ) _onBoardingState.value = OnBoardingScreenState.HOME
                    }

                    "need-signup" -> _onBoardingState.value = OnBoardingScreenState.SIGNUP
                }
            }.onFailure {
                _onBoardingState.value = OnBoardingScreenState.ERROR
            }
        }
    }

    fun signUp() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(screenState = UiState.LOADING)
            runCatching {
                val prefill = userInfo.value?.prefill ?: error("Missing prefill")
                val hint = userInfo.value?.hint ?: error("Missing hint")

                signUseCase.googleSignUp(
                    UserRequest(
                        providerId = hint.providerId,
                        email = prefill.email,
                        name = prefill.name,
                        pictureUrl = prefill.picture,
                        userId = _uiState.value.userId,
                        birthdate = _uiState.value.birthDate,
                        gender = _uiState.value.gender?.toGenderString() ?: "null"
                    )
                )
            }.onSuccess { response ->
                if (TokenUtils.saveAccessToken(response.accessToken) &&
                    TokenUtils.saveRefreshToken(response.refreshToken)
                ) {
                    _uiState.value = _uiState.value.copy(screenState = UiState.SUCCESS)
                } else {
                    _uiState.value = _uiState.value.copy(screenState = UiState.ERROR)
                }
            }.onFailure {
                _uiState.value = _uiState.value.copy(screenState = UiState.ERROR)
            }
        }
    }
}