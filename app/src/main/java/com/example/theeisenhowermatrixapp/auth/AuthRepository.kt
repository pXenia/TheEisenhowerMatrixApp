package com.example.theeisenhowermatrixapp.auth

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val apiService: AuthAPI,
    private val tokenManager: TokenManager
) {
    suspend fun login(username: String, password: String): Result<Unit> {
        return try {
            val response = apiService.login(username, password)
            tokenManager.saveToken(response.accessToken)
            Result.success(Unit)
        } catch (e: Exception) {
            Log.d("TAG", e.toString())
            Result.failure(e)
        }
    }

    suspend fun logout() {
        tokenManager.clearToken()
    }

    fun isLoggedIn(): Flow<Boolean> {
        return tokenManager.getToken().map { it != null }
    }

    suspend fun register(
        nickname: String,
        email: String,
        password: String
    ): Result<Unit> =
        runCatching {
            apiService.register(
                RegisterRequest(
                    nickname = nickname,
                    email = email,
                    password = password
                )
            )
        }

    suspend fun changePassword(
        oldPassword: String,
        newPassword: String
    ): Result<Unit> =
        runCatching {
            apiService.changePassword(
                ChangePasswordRequest(
                    oldPassword = oldPassword,
                    newPassword = newPassword
                )
            )
        }

    suspend fun getMe(): Result<UserProfile> =
        runCatching {
            apiService.getMe()
        }
}