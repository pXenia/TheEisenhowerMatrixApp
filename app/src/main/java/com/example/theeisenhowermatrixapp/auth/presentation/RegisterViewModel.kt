package com.example.theeisenhowermatrixapp.auth.presentation

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.theeisenhowermatrixapp.auth.data.AuthRepository
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
        _uiState.update { it.copy(nickname = value.trim(), error = null) }
    }

    fun onEmailChange(value: String) {
        _uiState.update { it.copy(email = value.trim(), error = null) }
    }

    fun onPasswordChange(value: String) {
        _uiState.update { it.copy(password = value, error = null) }
    }

    fun onConfirmPasswordChange(value: String) {
        _uiState.update { it.copy(confirmPassword = value, error = null) }
    }

    fun register(onSuccess: () -> Unit) {
        val state = _uiState.value

        // валидация имени
        if (state.nickname.isBlank()) {
            _uiState.update { it.copy(error = "Введите имя") }
            return
        }

        if (state.nickname.length < 3) {
            _uiState.update {
                it.copy(error = "Имя должно содержать минимум 3 символа")
            }
            return
        }

        // валидация почты
        if (state.email.isBlank()) {
            _uiState.update { it.copy(error = "Введите email") }
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(state.email).matches()) {
            _uiState.update {
                it.copy(error = "Введите корректный email")
            }
            return
        }

        // валидация пароля
        if (state.password.isBlank()) {
            _uiState.update { it.copy(error = "Введите пароль") }
            return
        }

        if (state.password.length < 6) {
            _uiState.update {
                it.copy(error = "Пароль должен содержать минимум 6 символов")
            }
            return
        }

        // подтверждение пароля
        if (state.password != state.confirmPassword) {
            _uiState.update {
                it.copy(error = "Пароли не совпадают")
            }
            return
        }

        // вызовы
        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true, error = null)
            }

            authRepository.register(
                nickname = state.nickname,
                email = state.email,
                password = state.password
            ).onSuccess {

                // автологин
                authRepository.login(state.email, state.password)
                onSuccess()

            }.onFailure { error ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = when {
                            error.message?.contains("email") == true ->
                                "Пользователь с таким email уже существует"
                            error.message?.contains("nickname") == true ->
                                "Пользователь с таким именем уже существует"
                            else ->
                                error.message ?: "Ошибка регистрации"
                        }
                    )
                }
            }

            _uiState.update { it.copy(isLoading = false) }
        }
    }
}