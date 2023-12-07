package com.example.practicapersonasapi.models

class Pelicula(
    val title: String,
    val synopsis: String,
    val year: Int,
    val durationMin: Int,
    val language: Int,
    val status: Int
){
    var id: Int = 0
    var image_extension: String = ""
    val posterUrl: String = ""
}