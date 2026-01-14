package com.example.theeisenhowermatrixapp.stats

import javax.inject.Inject

class StatsRepository @Inject constructor(
    private val api: StatsAPI
) {

    suspend fun getTaskStats(): Result<TaskStatsResponse> =
        runCatching { api.getTaskStats() }

    suspend fun getTimingStats(): Result<TimingStatsResponse> =
        runCatching { api.getTimingStats() }
}
