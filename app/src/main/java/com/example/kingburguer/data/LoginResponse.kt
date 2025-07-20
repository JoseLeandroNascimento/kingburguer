package com.example.kingburguer.data

import com.google.gson.annotations.SerializedName

data class LoginResponse(

    @SerializedName("access_token") val accessToken: String,

    @SerializedName("refresh_token") val refreshToken: String,

    @SerializedName("token_type") val tokenType: String,

    @SerializedName("expires_seconds") val expiresSeconds: Double,
)

data class Error(
    val detail: String
)

data class ErrorAuth(
    val detail: ErrorDetail
)
