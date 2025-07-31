package com.example.kingburguer.validations

import com.example.kingburguer.R

class NameValidator {

    companion object {

        fun validate(name: String): TextString? {
            if (name.isBlank()) {
                return ResourceString(input = R.string.error_name_blank)
            }

            if (name.length < 3) {
                return ResourceString(input = R.string.error_name_invalid)

            }
            return null
        }

    }
}