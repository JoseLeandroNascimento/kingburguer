package com.example.kingburguer.auth.data

import com.example.kingburguer.data.ErrorDetail

sealed class UserCreateResponse {

    data class Success(
        val id: Int,
        val name: String,
        val email: String,
        val document: String,
        val birthday: String
    ) : UserCreateResponse()

    data class Error(
        val detail: String
    ) : UserCreateResponse()

    data class ErrorAuth(
        val detail: ErrorDetail
    ) : UserCreateResponse()

}