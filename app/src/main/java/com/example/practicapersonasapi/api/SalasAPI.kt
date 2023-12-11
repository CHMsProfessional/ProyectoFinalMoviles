package com.example.practicaproductosapi.api


import com.example.practicapersonasapi.models.Sala
import com.example.practicapersonasapi.models.ScheduleIDRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface SalasAPI {
    @GET("rooms/{id}")
    fun getSala(@Header("Authorization") authorizationHeader: String,@Path("id") id: Int): Call<Sala>

    @POST("roombyschedule/{id}")
    fun getSalaWithVacant(@Header("Authorization") authorizationHeader: String,@Path("id") id: Int,@Body scheduleIDRequest: ScheduleIDRequest): Call<Sala>
}