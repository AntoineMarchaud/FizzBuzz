package com.amarchaud.fizzbuzz.domain

import com.amarchaud.fizzbuzz.domain.repository.FizzBuzzRepository
import com.amarchaud.fizzbuzz.domain.usecase.GetFizzBuzzUseCase
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever

class GetFizzBuzzUseCaseTest {

    private val repositoryMock: FizzBuzzRepository = mock()
    private lateinit var getFizzBuzzUseCase: GetFizzBuzzUseCase

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
        getFizzBuzzUseCase = GetFizzBuzzUseCase(repositoryMock)
    }

    @Test
    fun normalTest() = runTest {
        whenever(repositoryMock.computeFizzBuzz(any(), any(), any(), any(), any()))
            .thenReturn(Result.success(listResultMock))
        val expected = Result.success(listResultMock)

        val emitted = getFizzBuzzUseCase.run(3, 5, text1Mock, text2Mock, 20)

        Assert.assertTrue(emitted.isSuccess)
        Assert.assertEquals(emitted, expected)
    }
}