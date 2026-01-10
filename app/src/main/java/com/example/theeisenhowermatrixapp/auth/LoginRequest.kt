package com.example.theeisenhowermatrixapp.auth

data class LoginRequest(
    val username: String,
    val password: String,
    val grant_type: String = "password"
)