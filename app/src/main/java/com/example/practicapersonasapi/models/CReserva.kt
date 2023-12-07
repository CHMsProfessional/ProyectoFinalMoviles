package com.example.practicapersonasapi.models

data class CReserva(
    val schedule_id: Int,
    val card_number: String,
    val card_name: String,
    val card_date: String,
    val card_cvv: String,
    val tickets: List<Int>
)