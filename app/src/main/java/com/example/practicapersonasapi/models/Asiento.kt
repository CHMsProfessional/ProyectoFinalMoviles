package com.example.practicapersonasapi.models

data class Asiento (
    val roomID: Int,
    val row: Int,
    val column: Int
) {
    var id: Int = 0
}