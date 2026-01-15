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
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onUsernameChange(username: String) {
        _uiState.update { it.copy(username = username, error = null) }
    }

    fun onPasswordChange(password: String) {
        _uiState.update { it.copy(password = password, error = null) }
    }

    fun login(onSuccess: () -> Unit) {
        val username = _uiState.value.username.trim()
        val password = _uiState.value.password

        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true, error = null)
            }

            val result = authRepository.login(username, password)

            _uiState.update { it.copy(isLoading = false) }

            result.onSuccess {
                onSuccess()
            }.onFailure { error ->
                _uiState.update {
                    it.copy(
                        error = when {
                            error.message?.contains("401") == true -> "Неверный email или пароль"
                            else -> error.message ?: "Ошибка авторизации"
                        }
                    )
                }
            }
        }
    }
}