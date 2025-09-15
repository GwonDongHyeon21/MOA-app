package org.moa.moa.presentation.sign

import org.moa.moa.presentation.UiState

data class SignUpUiState(
    val userId: String,
    val birthDate: String,
    val gender: Gender?,
    val screenState: UiState,
)

enum class Gender {
    MEN, WOMEN
}