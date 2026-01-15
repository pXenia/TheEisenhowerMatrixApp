package com.example.theeisenhowermatrixapp.tasks.data

import com.example.theeisenhowermatrixapp.tasks.domain.CreateTaskRequest
import com.example.theeisenhowermatrixapp.tasks.domain.Task
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path


interface ToDoAPI {
    @GET("api/v3/tasks/quadrant/{quadrant}")
    suspend fun getTasksByQuadrant(
        @Path("quadrant") quadrant: String
    ): List<Task>

    @GET("api/v3/tasks")
    suspend fun getAllTasks(): List<Task>

    @POST("api/v3/tasks/")
    suspend fun createTask(
        @Body request: CreateTaskRequest
    ): Task

    @DELETE("api/v3/tasks/{id}")
    suspend fun deleteTask(
        @Path("id") id: Int
    )

    @PATCH("api/v3/tasks/{id}/change_status")
    suspend fun changeStatus(
        @Path("id") id: Int
    )

    @PUT("api/v3/tasks/{id}")
    suspend fun updateTask(
        @Path("id") id: Int,
        @Body request: CreateTaskRequest
    ): Task
}