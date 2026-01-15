package com.example.theeisenhowermatrixapp.stats.domain

import com.google.gson.annotations.SerializedName

data class TaskStatsResponse(
    @SerializedName("total_tasks")
    val totalTasks: Int,
    @SerializedName("by_quadrant")
    val byQuadrant: Map<String, Int>,
    @SerializedName("by_status")
    val byStatus: Map<String, Int>
)

data class TimingStatsResponse(
    @SerializedName("completed_on_time")
    val completedOnTime: Int,
    @SerializedName("completed_late")
    val completedLate: Int,
    @SerializedName("on_plan_pending")
    val onPlanPending: Int,
    @SerializedName("overtime_pending")
    val overtimePending: Int
)
