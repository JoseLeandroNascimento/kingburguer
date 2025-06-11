package com.example.kingburguer.composes.login

import com.example.kingburguer.composes.signup.FieldState

data class FormState(
    val email: FieldState = FieldState(),
    val password: FieldState = FieldState(),
    val rememberMe: Boolean = false,
    val formIsValid: Boolean = false
)