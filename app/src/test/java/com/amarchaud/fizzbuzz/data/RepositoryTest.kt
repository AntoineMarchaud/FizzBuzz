package com.amarchaud.fizzbuzz.data

import com.amarchaud.fizzbuzz.data.models.ErrorData
import com.amarchaud.fizzbuzz.data.repository.FizzBuzzRepositoryImpl
import com.amarchaud.fizzbuzz.domain.repository.FizzBuzzRepository
import com.amarchaud.fizzbuzz.domain.usecase.errors.toDomain
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class RepositoryTest {

    private lateinit var repository: FizzBuzzRepository
    private lateinit var ioDispatcher: CoroutineDispatcher

    private val text1Mock = "fizz"
    private val text2Mock = "buzz"
    private val listResultMock = listOf(
        "1",
        "2",
        text1Mock,
        "4",
        text2Mock,
        text1Mock,
        "7",
        "8",
        text1Mock,
        text2Mock,
        "11",
        text1Mock,
        "13",
        "14",
        "$text1Mock$text2Mock",
        "16",
        "17",
        text1Mock,
        "19",
        text2Mock
    )

    @Before
    fun setUp() {
        ioDispatcher = Dispatchers.Unconfined
        repository = FizzBuzzRepositoryImpl(ioDispatcher)
    }

    @Test
    fun allOk() = runTest {
        val res = repository.computeFizzBuzz(
            3, 5, text1Mock, text2Mock, 20
        )

        Assert.assertTrue(res.isSuccess)
        Assert.assertTrue(res == Result.success(listResultMock))
    }

    @Test
    fun error() = runTest {
        val res = repository.computeFizzBuzz(
            30, 5, text1Mock, text2Mock, 20
        )

        Assert.assertTrue(res.isFailure)
        Assert.assertEquals(res.exceptionOrNull()?.javaClass, ErrorData.GreaterThanLimit.toDomain().javaClass)
    }
}