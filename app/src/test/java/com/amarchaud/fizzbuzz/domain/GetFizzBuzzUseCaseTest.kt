package com.amarchaud.fizzbuzz.domain

import arrow.core.left
import com.amarchaud.fizzbuzz.domain.repository.FizzBuzzRepository
import com.amarchaud.fizzbuzz.domain.usecase.GetFizzBuzzUseCase
import com.amarchaud.fizzbuzz.domain.usecase.errors.UseCaseError
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class GetFizzBuzzUseCaseTest {

    private lateinit var repositoryMock : FizzBuzzRepository
    private lateinit var useCase : GetFizzBuzzUseCase

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
        repositoryMock = mockk()
        useCase = GetFizzBuzzUseCase(repositoryMock)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun normalTest() = runTest {
        coEvery {
            repositoryMock.computeFizzBuzz(any(),any(),any(),any(),any())
        } answers {
            listResultMock.left()
        }
        val expected = listResultMock.left()

        val emitted = useCase.run(3, 5, text1Mock, text2Mock, 20)

        Assert.assertTrue(emitted.isLeft())
        Assert.assertTrue(emitted == expected)
    }
}