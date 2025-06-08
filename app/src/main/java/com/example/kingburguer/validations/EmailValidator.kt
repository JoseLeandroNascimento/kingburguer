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
                return ResourceString(input = R.string.error_email_invalid)
            }

            return null
        }

        private fun isEmailValid(email: String): Boolean {
            return Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }
    }

}