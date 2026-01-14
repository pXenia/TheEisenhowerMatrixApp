package com.example.theeisenhowermatrixapp.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    fun onNicknameChange(value: String) {
        _uiState.update { it.copy(nickname = value, error = null) }
    }

    fun onEmailChange(value: String) {
        _uiState.update { it.copy(email = value, error = null) }
    }

    fun onPasswordChange(value: String) {
        _uiState.update { it.copy(password = value, error = null) }
    }

    fun onConfirmPasswordChange(value: String) {
        _uiState.update { it.copy(confirmPassword = value, error = null) }
    }

    fun register(onSuccess: () -> Unit) {
        val state = _uiState.value

        when {
            state.nickname.isBlank()
                    || state.email.isBlank()
                    || state.password.isBlank() ->
                _uiState.update { it.copy(error = "Заполните все поля") }

            state.password != state.confirmPassword ->
                _uiState.update { it.copy(error = "Пароли не совпадают") }

            else -> {
                viewModelScope.launch {
                    _uiState.update { it.copy(isLoading = true, error = null) }

                    authRepository.register(
                        nickname = state.nickname,
                        email = state.email,
                        password = state.password
                    ).onSuccess {
                        onSuccess()
                    }.onFailure {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                error = it.error ?: "Ошибка регистрации"
                            )
                        }
                    }

                    _uiState.update { it.copy(isLoading = false) }
                     authRepository.login(uiState.value.email, uiState.value.password)
                }
            }
        }
    }
}
