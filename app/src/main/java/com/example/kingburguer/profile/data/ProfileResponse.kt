package com.example.kingburguer.profile.data

import java.util.Date

data class ProfileResponse(
    val id: Int,
    val name: String,
    val email: String,
    val document: String,
    val birthday: Date
)