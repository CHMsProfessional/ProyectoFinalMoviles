package com.example.practicapersonasapi.models

data class Horarios(
    val movieID: Int,
    val roomID: Int,
    val date: String,
    val time: String,
    val availableSeats: Int,
    val totalSeats: Int,
    val price: Int,
    val status: Int
) {
    var id: Int = 0
    var room: Sala? = null
    var movie: Pelicula? = null
}