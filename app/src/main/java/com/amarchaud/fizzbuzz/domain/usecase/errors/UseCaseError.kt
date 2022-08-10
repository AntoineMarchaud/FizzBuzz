package com.amarchaud.fizzbuzz.domain.usecase.errors

import com.amarchaud.fizzbuzz.data.models.ErrorData

sealed class UseCaseError {
    object GenericUseCaseError : UseCaseError()
    object NegativeNumberUseCaseError : UseCaseError()
    object GreaterThanLimitUseCaseError : UseCaseError()

    companion object {
        internal fun handleError(error: ErrorData) = when (error) {
            is ErrorData.GreaterThanLimit -> GreaterThanLimitUseCaseError
            is ErrorData.NegativeNumber -> NegativeNumberUseCaseError
            else -> GenericUseCaseError
        }
    }
}