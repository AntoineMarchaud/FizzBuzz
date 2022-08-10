package com.amarchaud.fizzbuzz.domain.repository

import arrow.core.Either
import com.amarchaud.fizzbuzz.data.models.ErrorData

interface FizzBuzzRepository {
    suspend fun computeFizzBuzz(
        int1: Int,
        int2: Int,
        text1: String,
        text2: String,
        limit: Int
    ): Either<List<String>, ErrorData>
}