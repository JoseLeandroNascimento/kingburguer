package com.example.kingburguer.auth.presentation.signup

data class SignupUiState(
    val isLoading: Boolean = false,
    val goToLogin: Boolean = false,
    val error: String? = null
)