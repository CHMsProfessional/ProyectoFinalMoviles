package com.example.practicapersonasapi.models

data class Horario(
    var id: Int,
    val movie_id: Int,
    val room_id: Int,
    val date: String,
    val time: String,
    val available_seats: Int,
    val total_seats: Int,
    val price: Int,
    val status: Int
) {
    var room: Sala? = null
    var movie: Pelicula? = null
}