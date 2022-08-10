package com.amarchaud.fizzbuzz.data.models

sealed class ErrorData  {
    data object NegativeNumber : ErrorData()
    data object GreaterThanLimit : ErrorData()
}