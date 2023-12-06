package com.example.practicapersonasapi.repositories

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitRepository {
    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://cineapi.jmacboy.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}