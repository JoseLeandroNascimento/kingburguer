package com.example.kingburguer.composes.signup

import com.example.kingburguer.validations.TextString

data class FieldState(
    val field: String = "",
    val error: TextString? = null,
)

data class FormState(
    val email: FieldState = FieldState(),
    val name: FieldState = FieldState(),
    val password: FieldState = FieldState(),
    val confirmPassword: FieldState = FieldState(),
    val document: FieldState = FieldState(),
    val birthdate: FieldState = FieldState(),
)