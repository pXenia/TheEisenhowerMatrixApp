package com.example.theeisenhowermatrixapp.stats

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
