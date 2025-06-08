package com.example.kingburguer.validations

import android.util.Patterns
import com.example.kingburguer.R
import com.example.kingburguer.composes.signup.FieldState

class PasswordValidator {

    companion object {

        fun validate(password: String): TextString? {

            if (password.isBlank()) {
                return ResourceString(input = R.string.error_password_blanck)

            }

            if (password.length < 8) {
                return ResourceString(input = R.string.error_password_invalid)
            }

            return null
        }


    }
}