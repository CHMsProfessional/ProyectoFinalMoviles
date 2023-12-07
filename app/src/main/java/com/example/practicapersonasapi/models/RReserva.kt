package com.example.practicapersonasapi.models

data class RReserva(
    val id: Long,
    val user_id: Long,
    val schedule_id: Long,
    val totalPrice: Long,
    val code: String,
    val ingreso: Long,
    val qrUrl: String,
    val user: User,
    val schedule: Horario,
    val tickets: List<Ticket>
) {

}