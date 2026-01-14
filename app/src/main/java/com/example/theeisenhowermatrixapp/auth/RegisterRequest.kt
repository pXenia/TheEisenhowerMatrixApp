package com.example.theeisenhowermatrixapp.auth

data class RegisterRequest(
    val nickname: String,
    val email: String,
    val password: String
)
