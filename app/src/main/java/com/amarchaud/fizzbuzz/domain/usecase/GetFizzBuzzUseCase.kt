package com.amarchaud.fizzbuzz.domain.usecase

import arrow.core.Either
import com.amarchaud.fizzbuzz.domain.repository.FizzBuzzRepository
import com.amarchaud.fizzbuzz.domain.usecase.errors.UseCaseError
import javax.inject.Inject

class GetFizzBuzzUseCase @Inject constructor(
    private val repository: FizzBuzzRepository
) {
    suspend fun run(
        int1: Int,
        int2: Int,
        text1: String,
        text2: String,
        limit: Int
    ): Either<List<String>, UseCaseError> {
        return repository.computeFizzBuzz(int1, int2, text1, text2, limit)
            .map { UseCaseError.handleError(it) }
    }
}