package com.example.theeisenhowermatrixapp.stats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.theeisenhowermatrixapp.auth.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StatsViewModel @Inject constructor(
    private val statsRepository: StatsRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(StatsUiState())
    val uiState: StateFlow<StatsUiState> = _uiState.asStateFlow()

    init {
        loadStats()
        loadUser()
    }

    private fun loadUser() {
        viewModelScope.launch {
            val result = authRepository.getMe()

            result.onSuccess { profile ->
                _uiState.update {
                    it.copy(
                        username = profile.nickname,
                        email = profile.email
                    )
                }
            }.onFailure { error ->
                _uiState.update {
                    it.copy(
                        error = error.message ?: "Не удалось загрузить профиль"
                    )
                }
            }
        }
    }


    fun loadStats() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            val taskStats = statsRepository.getTaskStats()
            val timingStats = statsRepository.getTimingStats()

            if (taskStats.isSuccess && timingStats.isSuccess) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        taskStats = taskStats.getOrNull(),
                        timingStats = timingStats.getOrNull()
                    )
                }
            } else {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = taskStats.exceptionOrNull()?.message
                            ?: timingStats.exceptionOrNull()?.message
                    )
                }
            }
        }
    }

    fun changePassword(old: String, new: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isChangingPassword = true, error = null) }

            val result = authRepository.changePassword(old, new)

            _uiState.update {
                it.copy(
                    isChangingPassword = false,
                    passwordChanged = result.isSuccess,
                    error = result.exceptionOrNull()?.message
                )
            }
        }
    }

    fun resetPasswordChanged() {
        _uiState.update { it.copy(passwordChanged = false) }
    }
}

