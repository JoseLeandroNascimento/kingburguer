package com.example.kingburguer.composes.signup

data class SignupUiState(
    val isLoading: Boolean = false,
    val goToHome: Boolean = false,
    val error: String? = null
)