package com.example.theeisenhowermatrixapp.auth.presentation

data class LoginUiState(
    val username: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)