package com.example.kingburguer.validations

import android.util.Patterns
import com.example.kingburguer.R

class EmailValidator {

    companion object {

        fun validate(email: String): TextString? {
            if (email.isBlank()) {
                return ResourceString(input = R.string.error_email_blank)
            }

            if (!isEmailValid(email)) {
                return RawString(input = "E-mail inválido. Verifique o campo novamente.")
            }

            return null
        }

        private fun isEmailValid(email: String): Boolean {
            return Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }
    }

}