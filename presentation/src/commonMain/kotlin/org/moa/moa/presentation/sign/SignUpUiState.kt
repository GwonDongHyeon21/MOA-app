package org.moa.moa.presentation.sign

import org.moa.moa.presentation.UiState
import org.moa.moa.presentation.sign.Gender.MEN
import org.moa.moa.presentation.sign.Gender.WOMEN

data class SignUpUiState(
    val userId: String,
    val birthDate: String,
    val gender: Gender?,
    val screenState: UiState,
)

enum class OnBoardingScreenState {
    ONBOARDING,
    LOADING,
    SIGNUP,
    HOME,
    ERROR
}

enum class Gender {
    MEN,
    WOMEN
}

fun Gender.toGenderString(): String {
    return when (this) {
        MEN -> "male"
        WOMEN -> "female"
    }
}