package com.example.practicapersonasapi.models

data class RReserva(
    val id: Long,
    val userID: Long,
    val scheduleID: Long,
    val totalPrice: Long,
    val code: String,
    val ingreso: Long,
    val createdAt: String,
    val updatedAt: String,
    val qrURL: String,
    val user: User,
    val schedule: Horarios,
    val tickets: List<Ticket>
) {

}