package com.example.practicapersonasapi.api

import com.example.practicapersonasapi.models.Pelicula
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface PeliculasAPI {
    @GET("movies/premiere")
    fun getPeliculasEstrenosList(@Header("Authorization") authorizationHeader: String): Call<List<Pelicula>>

    @GET("movies/presale")
    fun getPeliculasPreventaList(@Header("Authorization") authorizationHeader: String): Call<List<Pelicula>>

    @GET("movies/billboard")
    fun getPeliculasCarteleraList(@Header("Authorization") authorizationHeader: String): Call<List<Pelicula>>

    @GET("movies/comingsoon")
    fun getPeliculasProximamenteList(@Header("Authorization") authorizationHeader: String): Call<List<Pelicula>>

    @GET("movies/{id}")
    fun getPelicula(@Header("Authorization") authorizationHeader: String,@Path("id") id: Int): Call<Pelicula>
}