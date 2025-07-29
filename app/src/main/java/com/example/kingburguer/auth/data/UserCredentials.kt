package com.example.kingburguer.auth.data

data class UserCredentials(
    val accessToken: String = "",
    val refreshToken: String = "",
    val expiresTimestamp: Long = 0,
    val tokenType: String = ""
)