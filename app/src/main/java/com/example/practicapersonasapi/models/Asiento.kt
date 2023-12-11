package com.example.practicapersonasapi.models

data class Asiento (
    val roomID: Int,
    val row: Int,
    val column: Int,
    val vacant: Boolean
) {
    var id: Int = 0
}