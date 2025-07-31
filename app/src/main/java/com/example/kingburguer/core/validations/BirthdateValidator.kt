package com.example.kingburguer.validations

import com.example.kingburguer.R
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class BirthdateValidator {

    companion object {

        fun validate(value: String, pattern: String): TextString? {

            if (value.length != pattern.length) {
                return ResourceString(input = R.string.error_birhdate_blank)
            }

            try {
                SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).run {
                    isLenient = false
                    parse(value)
                }?.also {

                    val now = Date()
                    if (it.after(now)) {
                        return ResourceString(input = R.string.error_birhdate_after)
                    }
                }

            } catch (e: ParseException) {
                return ResourceString(input = R.string.error_birhdate_blank)
            }

            return null
        }


    }
}