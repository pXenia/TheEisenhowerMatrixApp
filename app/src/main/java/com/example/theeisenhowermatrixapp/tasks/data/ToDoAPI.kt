package com.example.theeisenhowermatrixapp.tasks.data

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ToDoAPI {
    @GET("api/v3/tasks/quadrant/{quadrant}")
    suspend fun getTasksByQuadrant(
        @Path("quadrant") quadrant: String
    ): List<Task>

    @GET("api/v3/tasks")
    suspend fun getAllTasks(): List<Task>
}