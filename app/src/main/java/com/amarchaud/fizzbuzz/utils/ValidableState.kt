package com.amarchaud.fizzbuzz.utils

import java.util.regex.Pattern

sealed class ValidableState {
    fun isError() = this is Error
    fun isValid() = this is Valid

    object Neutral : ValidableState()
    object Valid : ValidableState()
    open class Error : ValidableState()
}

sealed class GenericErrorState : ValidableState.Error() {
    object Empty : GenericErrorState()
    object Syntax : GenericErrorState()
    object NumberTooBig : GenericErrorState()
}

private val numberPatter = Pattern.compile("^[+]?\\d+([.]\\d+)?\$")

fun integerValidatorWithMax(number: String?, valueMax: Int? = Int.MAX_VALUE) =
    if (number.isNullOrBlank()) {
        GenericErrorState.Empty
    } else if (!numberPatter.matcher(number).matches()) {
        GenericErrorState.Syntax
    } else if (valueMax != null && number.toLong() > valueMax) {
        GenericErrorState.NumberTooBig
    } else {
        ValidableState.Valid
    }

fun textValidator(text: String?) = if (text.isNullOrBlank()) {
    GenericErrorState.Empty
} else {
    ValidableState.Valid
}


fun ValidableState.takeIfOrElse(
    ifValue: ValidableState,
    elseValue: ValidableState
): ValidableState {
    return run {
        if (this == ifValue) this
        else elseValue
    }
}
