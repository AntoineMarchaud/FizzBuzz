package com.amarchaud.fizzbuzz.domain.repository

interface FizzBuzzRepository {
    suspend fun computeFizzBuzz(
        int1: Int,
        int2: Int,
        text1: String,
        text2: String,
        limit: Int
    ): Result<List<String>>
}