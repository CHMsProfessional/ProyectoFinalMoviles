package com.example.practicapersonasapi.models

data class Ticket(
    val id: Long,
    val seat_id: Long,
    val priceSold: Long,
    val reservation_id: Long,
    val seat: Asiento
) {
}