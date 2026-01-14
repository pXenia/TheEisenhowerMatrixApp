package com.example.theeisenhowermatrixapp.stats

import retrofit2.http.GET

interface StatsAPI {

    @GET("api/v3/stats")
    suspend fun getTaskStats(): TaskStatsResponse

    @GET("api/v3/stats/timing")
    suspend fun getTimingStats(): TimingStatsResponse
}
