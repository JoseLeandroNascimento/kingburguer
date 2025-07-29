package com.example.kingburguer.auth.presentation.login

import com.example.kingburguer.auth.presentation.signup.FieldState

data class FormState(
    val email: FieldState = FieldState(),
    val password: FieldState = FieldState(),
    val rememberMe: Boolean = false,
    val formIsValid: Boolean = false
)