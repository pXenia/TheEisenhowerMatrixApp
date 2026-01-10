package com.example.theeisenhowermatrixapp.tasks.data

import javax.inject.Inject

class TaskRepository @Inject constructor(
    private val toDoAPI: ToDoAPI
) {
    suspend fun getTasksByQuadrant(quadrant: String): Result<List<Task>> {
        return try {
            val tasks = toDoAPI.getTasksByQuadrant(quadrant)
            Result.success(tasks)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getAllTasks(): Result<List<Task>> {
        return try {
            val tasks = toDoAPI.getAllTasks()
            Result.success(tasks)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}