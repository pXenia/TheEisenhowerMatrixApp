package com.example.theeisenhowermatrixapp.tasks.domain

import com.google.gson.annotations.SerializedName

data class Task(
    val id: Int,
    val title: String,
    val description: String,
    @SerializedName("is_important")
    val isImportant: Boolean,
    @SerializedName("is_urgent")
    val isUrgent: Boolean,
    val quadrant: String,
    val completed: Boolean,
    @SerializedName("deadline_at")
    val deadlineAt: String?,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("completed_at")
    val completedAt: String?,
    @SerializedName("days_until_deadline")
    val daysUntilDeadline: Int?
)