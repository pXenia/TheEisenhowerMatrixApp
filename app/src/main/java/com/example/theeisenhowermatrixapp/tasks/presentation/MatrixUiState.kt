package com.example.theeisenhowermatrixapp.tasks.presentation

import com.example.theeisenhowermatrixapp.tasks.data.Task

data class MatrixUiState(
    val q1Tasks: List<Task> = emptyList(),
    val q2Tasks: List<Task> = emptyList(),
    val q3Tasks: List<Task> = emptyList(),
    val q4Tasks: List<Task> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)