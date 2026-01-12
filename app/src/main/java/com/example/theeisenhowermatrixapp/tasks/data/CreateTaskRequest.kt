package com.example.theeisenhowermatrixapp.tasks.data

import com.google.gson.annotations.SerializedName

data class CreateTaskRequest(
    val title: String,
    val description: String,
    @SerializedName("is_important")
    val isImportant: Boolean,
    @SerializedName("deadline_at")
    val deadlineAt: String
)