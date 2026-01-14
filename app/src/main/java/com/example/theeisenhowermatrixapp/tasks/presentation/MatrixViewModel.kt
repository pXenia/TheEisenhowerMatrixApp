package com.example.theeisenhowermatrixapp.tasks.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.theeisenhowermatrixapp.auth.AuthRepository
import com.example.theeisenhowermatrixapp.tasks.data.CreateTaskRequest
import com.example.theeisenhowermatrixapp.tasks.data.Task
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

    private val _allTasks = MutableStateFlow<List<Task>>(emptyList())
    val allTasks: StateFlow<List<Task>> = _allTasks.asStateFlow()

    init {
        viewModelScope.launch {
            authRepository.login("ksn@example.com", "111111")
            loadAllTasks()
        }
    }

    fun loadAllTasks() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            repository.getAllTasks()
                .onSuccess { tasks ->
                    _allTasks.value = tasks
                    updateQuadrants(tasks)
                    _uiState.update { it.copy(isLoading = false) }
                }
                .onFailure { e ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = e.message
                        )
                    }
                }
        }
    }

    fun retry() {
        loadAllTasks()
    }

    fun addTask(request: CreateTaskRequest) {
        viewModelScope.launch {
            repository.createTask(request)
                .onSuccess { newTask ->
                    val updated = listOf(newTask) + _allTasks.value
                    _allTasks.value = updated
                    updateQuadrants(updated)
                }
                .onFailure { e ->
                    _uiState.update { it.copy(error = e.message) }
                }
        }
    }

    fun deleteTask(id: Int) {
        viewModelScope.launch {

            val updated = _allTasks.value.filterNot { it.id == id }
            _allTasks.value = updated
            updateQuadrants(updated)

            repository.deleteTask(id)
                .onFailure { e ->
                    _uiState.update { it.copy(error = e.message) }
                }
        }
    }

    fun changeStatus(id: Int) {
        viewModelScope.launch {

            val updated = _allTasks.value.map { task ->
                if (task.id == id) {
                    task.copy(completed = !task.completed)
                } else {
                    task
                }
            }

            _allTasks.value = updated
            updateQuadrants(updated)

            repository.changeStatus(id)
                .onFailure { e ->
                    _uiState.update { it.copy(error = e.message) }
                }
        }
    }
    private fun updateQuadrants(tasks: List<Task>) {
        _uiState.update {
            it.copy(
                q1Tasks = tasks.filter { it.quadrant == "Q1" },
                q2Tasks = tasks.filter { it.quadrant == "Q2" },
                q3Tasks = tasks.filter { it.quadrant == "Q3" },
                q4Tasks = tasks.filter { it.quadrant == "Q4" }
            )
        }
    }
}
