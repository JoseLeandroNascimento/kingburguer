package com.example.kingburguer.validations

fun mask(pattern: String, currentValue: String, value: String): String {

    val str = value.filter { it.isLetterOrDigit() }

    var result = ""
    var i = 0
    for (char in pattern) {
        if (char != '#') {
            result += char

            if (currentValue > value && result.length >= value.length) {
                result = result.dropLast(1)
            }
            continue
        }

        if (i >= str.length) break

        result += str[i]
        i++
    }

    return result
}