package com.example.kingburguer.validations

import com.example.kingburguer.R

class DocumentValidator {

    companion object {


        fun validate(document: String,pattern: String): TextString? {

            if (document.isBlank()) {
                return ResourceString(input = R.string.error_document_blank)
            }

            if (document.length != pattern.length) {
                return ResourceString(input = R.string.error_document_invalid)
            }

            return null
        }
    }
}