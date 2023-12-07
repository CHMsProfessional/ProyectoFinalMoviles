package com.example.practicapersonasapi.api

import com.example.practicapersonasapi.models.CReserva
import com.example.practicapersonasapi.models.RReserva
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ReservasAPI {
    @GET("reservations/")
    fun getReservasUsuario(@Header("Authorization") authorizationHeader: String): Call<List<RReserva>>

    @POST("reservations")
    fun createReserva(@Header("Authorization") authorizationHeader: String,@Body reserva: CReserva): Call<RReserva>


}