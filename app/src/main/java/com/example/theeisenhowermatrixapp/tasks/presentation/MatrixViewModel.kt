package com.example.theeisenhowermatrixapp.tasks.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.theeisenhowermatrixapp.auth.AuthRepository
import com.example.theeisenhowermatrixapp.tasks.data.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MatrixViewModel @Inject constructor(
    private val repository: TaskRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MatrixUiState())
    val uiState: StateFlow<MatrixUiState> = _uiState.asStateFlow()

    init {
        loadAllTasks()
    }

    fun loadAllTasks() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            val q1Deferred = async { repository.getTasksByQuadrant("Q1") }
            val q2Deferred = async { repository.getTasksByQuadrant("Q2") }
            val q3Deferred = async { repository.getTasksByQuadrant("Q3") }
            val q4Deferred = async { repository.getTasksByQuadrant("Q4") }

            val q1Result = q1Deferred.await()
            val q2Result = q2Deferred.await()
            val q3Result = q3Deferred.await()
            val q4Result = q4Deferred.await()

            val error = listOf(q1Result, q2Result, q3Result, q4Result)
                .firstOrNull { it.isFailure }
                ?.exceptionOrNull()
                ?.message

            _uiState.update {
                it.copy(
                    q1Tasks = q1Result.getOrDefault(emptyList()),
                    q2Tasks = q2Result.getOrDefault(emptyList()),
                    q3Tasks = q3Result.getOrDefault(emptyList()),
                    q4Tasks = q4Result.getOrDefault(emptyList()),
                    isLoading = false,
                    error = error
                )
            }
        }
    }

    fun retry() {
        loadAllTasks()
    }
}