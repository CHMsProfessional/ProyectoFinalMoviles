package com.example.practicapersonasapi.api

import com.example.practicapersonasapi.models.CReserva
import com.example.practicapersonasapi.models.QRCodeReserva
import com.example.practicapersonasapi.models.RReserva
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ReservasAPI {
    @GET("reservations/")
    fun getReservasUsuario(@Header("Authorization") authorizationHeader: String): Call<List<RReserva>>

    @POST("reservations")
    fun createReserva(@Header("Authorization") authorizationHeader: String,@Body reserva: CReserva): Call<RReserva>

    @POST("reservations/validate")
    fun validateReserva(@Header("Authorization") authorizationHeader: String,@Body reserva: QRCodeReserva): Call<RReserva>

    @POST("reservations/{id}/use")
    fun useReserva(@Header("Authorization") authorizationHeader: String,@Path("id") id: Int,@Body reserva: QRCodeReserva): Call<RReserva>




}