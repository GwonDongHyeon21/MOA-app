package org.moa.moa.presentation.sign

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moa.domain.model.User
import com.moa.domain.usecase.SignUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SignUpViewModel(private val signUseCase: SignUseCase) : ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun signUp(userId: String, birthDate: String, gender: Int) {
        viewModelScope.launch {
            runCatching {
                signUseCase.signUp(User(userId, birthDate, gender))
            }.onSuccess {
                // 회원가입 성공
            }.onFailure {
                it.printStackTrace()
            }
        }
    }
}