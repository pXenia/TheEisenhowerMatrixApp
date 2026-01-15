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

    suspend fun createTask(request: CreateTaskRequest): Result<Task> {
        return try {
            Result.success(toDoAPI.createTask(request))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteTask(id: Int): Result<Unit> {
        return try {
            toDoAPI.deleteTask(id)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun changeStatus(id: Int): Result<Unit> {
        return try {
            toDoAPI.changeStatus(id)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateTask(id: Int, request: CreateTaskRequest): Result<Task> {
        return try {
            Result.success(toDoAPI.updateTask(id, request))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}