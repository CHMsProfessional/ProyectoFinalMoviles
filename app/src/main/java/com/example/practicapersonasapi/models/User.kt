package com.example.practicapersonasapi.models

data class User(
    val name: String,
    val email: String,
    val password: String,
    var authToken: String? = null,
    var roles: List<String>? = null
)