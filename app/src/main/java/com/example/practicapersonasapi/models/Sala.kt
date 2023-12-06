package com.example.practicapersonasapi.models

data class Sala (
    val name: String,
    val rows: Int,
    val columns: Int,

) {
    var id: Int = 0
    var seats: List<Asiento> = emptyList()
}