package org.moa.moa.presentation.sign

import org.moa.moa.presentation.UiState
import org.moa.moa.presentation.sign.Gender.*

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
    WOMEN,
    UNKNOWN
}

fun Gender?.toGenderString(): String {
    return when (this) {
        MEN -> "male"
        WOMEN -> "female"
        UNKNOWN -> "null"
        else -> "null"
    }
}