package com.example.kingburguer.validations

import androidx.compose.runtime.Composable

class RawString(private val input: String) : TextString {

    override val value: String
        @Composable
        get() = input


}