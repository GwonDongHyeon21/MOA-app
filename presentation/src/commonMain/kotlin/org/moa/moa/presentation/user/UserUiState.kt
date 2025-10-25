package org.moa.moa.presentation.user

import org.moa.moa.presentation.UiState

data class UserUiState(
    val personas: List<Pair<String, Int>>,
    val settings: List<String>,
    val screenState: UiState,
)