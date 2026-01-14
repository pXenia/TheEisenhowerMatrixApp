package com.example.theeisenhowermatrixapp.auth

import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST


interface AuthAPI {
    @FormUrlEncoded
    @POST("api/v3/auth/login")
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("grant_type") grantType: String = "password"
    ): LoginResponse

    @POST("api/v3/auth/register")
    suspend fun register(
        @Body request: RegisterRequest
    )

    @GET("api/v3/auth/me")
    suspend fun getMe(): UserProfile

    @PATCH("api/v3/auth/change-password")
    suspend fun changePassword(
        @Body request: ChangePasswordRequest
    )
}