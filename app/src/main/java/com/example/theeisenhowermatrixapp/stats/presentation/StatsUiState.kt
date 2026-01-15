package com.example.theeisenhowermatrixapp.stats.presentation

import com.example.theeisenhowermatrixapp.stats.domain.TaskStatsResponse
import com.example.theeisenhowermatrixapp.stats.domain.TimingStatsResponse

data class StatsUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val taskStats: TaskStatsResponse? = null,
    val timingStats: TimingStatsResponse? = null,
    val username: String = "",
    val email: String = "",
    val isChangingPassword: Boolean = false,
    val passwordChanged: Boolean = false
)