package com.amarchaud.fizzbuzz.data.repository

import com.amarchaud.fizzbuzz.data.models.ErrorData
import com.amarchaud.fizzbuzz.di.DispatcherModule
import com.amarchaud.fizzbuzz.domain.repository.FizzBuzzRepository
import com.amarchaud.fizzbuzz.domain.usecase.errors.toDomain
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FizzBuzzRepositoryImpl @Inject constructor(
    @DispatcherModule.IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : FizzBuzzRepository {

    /**
     * Des trucs sont pas précisé dans l'exo
     * que doit on afficher si le nombre courant est a la fois un multiple de int1 et int2 : exemple 2 et 6 que dois ton afficher sur 6 ?
     * que doit on afficher si le nombre courant est a la fois un multiple de int1 et int2 et de (int1 * int2) : exemple : 2 et 2 que doit on afficher sur 2, 4, 6 etc ?
     */
    override suspend fun computeFizzBuzz(
        int1: Int,
        int2: Int,
        text1: String,
        text2: String,
        limit: Int
    ): Result<List<String>> = withContext(ioDispatcher) {
        if (int1 < 0 || int2 < 0) {
            Result.failure(ErrorData.NegativeNumber.toDomain())// normally
        } else if (int1 > limit || int2 > limit) {
            Result.failure(ErrorData.GreaterThanLimit.toDomain())
        } else {
            val l = buildList {
                for (i in 1..limit) {
                    add(
                        when {
                            i % (int1 * int2) == 0 -> "$text1$text2"
                            (i % int1 == 0 && i % int2 == 0) -> "!$text1/$text2!" // todo pas précisé dans l'exo
                            i % int1 == 0 -> text1
                            i % int2 == 0 -> text2
                            else -> i.toString()
                        }
                    )
                }
            }

            Result.success(l)
        }
    }
}