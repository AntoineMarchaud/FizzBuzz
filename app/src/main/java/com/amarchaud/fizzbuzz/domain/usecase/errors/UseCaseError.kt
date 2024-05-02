package com.amarchaud.fizzbuzz.domain.usecase.errors

import com.amarchaud.fizzbuzz.data.models.ErrorData

sealed class UseCaseError : Throwable() {
    class  GenericUseCaseError : UseCaseError()
    class NegativeNumberUseCaseError : UseCaseError()
    class GreaterThanLimitUseCaseError : UseCaseError()
}

fun ErrorData.toDomain() : UseCaseError {
    return  when (this) {
        is ErrorData.GreaterThanLimit -> UseCaseError.GreaterThanLimitUseCaseError()
        is ErrorData.NegativeNumber -> UseCaseError.NegativeNumberUseCaseError()
        else -> UseCaseError.GenericUseCaseError()
    }
}