package com.amarchaud.fizzbuzz.data

import arrow.core.left
import arrow.core.right
import com.amarchaud.fizzbuzz.data.models.ErrorData
import com.amarchaud.fizzbuzz.data.repository.FizzBuzzRepositoryImpl
import com.amarchaud.fizzbuzz.domain.repository.FizzBuzzRepository
import io.mockk.every
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
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

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun AllOk() = runTest {
        val res = repository.computeFizzBuzz(
            3, 5, text1Mock, text2Mock, 20
        )

        Assert.assertTrue(res == listResultMock.left() )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun Error() = runTest {
        val res = repository.computeFizzBuzz(
            30, 5, text1Mock, text2Mock, 20
        )

        Assert.assertTrue(res == ErrorData.GreaterThanLimit.right() )
    }
}