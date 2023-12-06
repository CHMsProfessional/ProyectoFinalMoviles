package com.example.practicapersonasapi.api

import com.example.practicapersonasapi.models.AuthUser
import com.example.practicapersonasapi.models.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AutenticacionAPI {
    @POST("registeruser")
    fun registerUser(@Body user: User): Call<User>

    @POST("registeradmin")
    fun registerAdmin(@Body user: User): Call<User>

    @POST("loginuser")
    fun loginUser(@Body user: User): Call<AuthUser>

    @GET("me")
    fun getUser(@Header("Authorization") authorizationHeader: String): Call<User>
}