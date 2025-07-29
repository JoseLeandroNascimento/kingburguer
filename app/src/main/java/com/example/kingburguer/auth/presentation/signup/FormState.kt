package com.example.kingburguer.auth.presentation.signup

import com.example.kingburguer.validations.TextString

data class FieldState(
    val field: String = "",
    val error: TextString? = null,
    val isValid: Boolean = false
)

data class FormState(
    val email: FieldState = FieldState(),
    val name: FieldState = FieldState(),
    val password: FieldState = FieldState(),
    val confirmPassword: FieldState = FieldState(),
    val document: FieldState = FieldState(),
    val birthdate: FieldState = FieldState(),
    val formIsValid: Boolean = false
)