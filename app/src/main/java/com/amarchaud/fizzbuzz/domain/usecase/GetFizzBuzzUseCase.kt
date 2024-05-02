package com.amarchaud.fizzbuzz.domain.usecase

import com.amarchaud.fizzbuzz.domain.repository.FizzBuzzRepository
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
    ): Result<List<String>> {
        return repository.computeFizzBuzz(int1, int2, text1, text2, limit)
    }
}