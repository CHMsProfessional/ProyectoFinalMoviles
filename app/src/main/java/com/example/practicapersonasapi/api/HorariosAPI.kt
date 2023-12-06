package com.example.practicaproductosapi.api

import com.example.practicapersonasapi.models.Horarios
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface HorariosAPI {
    @GET("movies/{id}/schedules")
    fun getHorariosPelicula(@Header("Authorization") authorizationHeader: String,@Path("id") id: Int): Call<List<Horarios>>
}