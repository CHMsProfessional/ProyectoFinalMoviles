package com.example.practicapersonasapi.models

data class Productos(
    val nombre: String,
    val descripcion: String,
    val precio_actual: String,
    val categoria_id: Int
) {
    var id: Int = 0
    //var categoria: Categorias? = null
    var createdAt: String? = null
    var updatedAt: String? = null
}
