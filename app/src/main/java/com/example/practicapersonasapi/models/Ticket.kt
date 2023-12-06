package com.example.practicapersonasapi.models

data class Ticket(
    val id: Long,
    val seatID: Long,
    val priceSold: Long,
    val reservationID: Long,
    val seat: Asiento
) {
}