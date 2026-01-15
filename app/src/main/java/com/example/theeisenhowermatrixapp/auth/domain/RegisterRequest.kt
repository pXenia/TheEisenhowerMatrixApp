package com.example.theeisenhowermatrixapp.auth.domain

data class RegisterRequest(
    val nickname: String,
    val email: String,
    val password: String
)