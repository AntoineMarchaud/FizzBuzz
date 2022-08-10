package com.amarchaud.fizzbuzz.utils

import androidx.annotation.StringRes

data class InputWrapper(
    val value: String = "",
    val state: ValidableState = ValidableState.Neutral,
    @StringRes val errorId: Int? = null,
    val validator: (value: String, param: String?) -> ValidableState = { _, _ -> ValidableState.Neutral }
)

fun InputWrapper.validate(param: String? = null) = validator(value, param)