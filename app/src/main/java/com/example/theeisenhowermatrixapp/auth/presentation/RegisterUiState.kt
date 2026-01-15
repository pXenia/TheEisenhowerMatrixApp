package com.example.theeisenhowermatrixapp.auth.presentation

data class RegisterUiState(
    val nickname: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)