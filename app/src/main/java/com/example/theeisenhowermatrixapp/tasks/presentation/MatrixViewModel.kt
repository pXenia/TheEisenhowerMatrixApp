package com.example.theeisenhowermatrixapp.tasks.presentation

import android.os.Build
import androidx.annotation.RequiresApi
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
import java.time.Instant
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
            loadAllTasks()
        }
    }

    fun loadAllTasks() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            repository.getAllTasks()
                .onSuccess { tasks ->
                    val sorted = sortTasks(tasks)
                    _allTasks.value = sorted
                    updateQuadrants(sorted)
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
                    val updated = sortTasks(listOf(newTask) + _allTasks.value)
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

            val updated = sortTasks(
                _allTasks.value.filterNot { it.id == id }
            )
            _allTasks.value = updated
            updateQuadrants(updated)

            repository.deleteTask(id)
                .onFailure { e ->
                    _uiState.update { it.copy(error = e.message) }
                }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun changeStatus(id: Int) {
        viewModelScope.launch {

            val updated = _allTasks.value.map { task ->
                if (task.id == id) {
                    task.copy(
                        completed = !task.completed,
                        completedAt = if (!task.completed)
                            Instant.now().toString()
                        else null
                    )
                } else task
            }

            val sorted = sortTasks(updated)
            _allTasks.value = sorted
            updateQuadrants(sorted)

            repository.changeStatus(id)
                .onFailure { e ->
                    _uiState.update { it.copy(error = e.message) }
                }
        }
    }

    fun updateTask(id: Int, request: CreateTaskRequest) {
        viewModelScope.launch {
            repository.updateTask(id, request)
                .onSuccess { updatedTask ->
                    val updated = _allTasks.value.map { task ->
                        if (task.id == id) updatedTask else task
                    }
                    val sorted = sortTasks(updated)
                    _allTasks.value = sorted
                    updateQuadrants(sorted)
                }
                .onFailure { e ->
                    _uiState.update { it.copy(error = e.message) }
                }
        }
    }

    private fun sortTasks(tasks: List<Task>): List<Task> {
        val activeTasks = tasks
            .filter { !it.completed }

        val completedTasks = tasks
            .filter { it.completed }
            .sortedByDescending { it.completedAt }

        return activeTasks + completedTasks
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
