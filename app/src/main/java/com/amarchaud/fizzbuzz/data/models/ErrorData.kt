package com.amarchaud.fizzbuzz.data.models

sealed class ErrorData {
    object NegativeNumber : ErrorData()
    object GreaterThanLimit : ErrorData()
}