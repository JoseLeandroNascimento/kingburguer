package com.example.kingburguer.validations

import com.example.kingburguer.R

class ConfirmPassword {

    companion object {

        fun validate(confirmPassword: String, password: String): TextString? {
            if (confirmPassword.isBlank()) {
                return ResourceString(input = R.string.error_confirm_password_blank)

            }

            if (confirmPassword != password) {
                return ResourceString(input = R.string.error_confirm_password_invalid)
            }

            return null
        }


    }
}