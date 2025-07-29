package com.example.kingburguer.auth.data

data class UserCreateRequest(
    val name: String,
    val email: String,
    val password: String,
    val document: String,
    val birthday: String
)