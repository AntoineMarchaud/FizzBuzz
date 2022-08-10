package com.amarchaud.fizztext2Mock.presentation

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import arrow.core.left
import arrow.core.right
import com.amarchaud.fizzbuzz.domain.usecase.GetFizzBuzzUseCase
import com.amarchaud.fizzbuzz.domain.usecase.errors.UseCaseError
import com.amarchaud.fizzbuzz.ui.MainActivity
import com.amarchaud.fizzbuzz.ui.screen.result.ResultComposableViewModel
import com.amarchaud.fizzbuzz.ui.screen.result.models.ResultUiModel
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.rules.TestRule

class ResultViewModelTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

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

    private lateinit var viewModel: ResultComposableViewModel
    private lateinit var applicationMock: Application
    private lateinit var useCaseMock: GetFizzBuzzUseCase
    private lateinit var savedStateHandleMock: SavedStateHandle

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        applicationMock = mockk()
        useCaseMock = mockk()
        savedStateHandleMock = mockk()
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun mockEnteredValues() {
        every {
            savedStateHandleMock.get<Int>(MainActivity.ResultExtra.integer1)
        } answers {
            2
        }

        every {
            savedStateHandleMock.get<Int>(MainActivity.ResultExtra.integer2)
        } answers {
            6
        }

        every {
            savedStateHandleMock.get<String>(MainActivity.ResultExtra.text1)
        } answers {
            text1Mock
        }

        every {
            savedStateHandleMock.get<String>(MainActivity.ResultExtra.text2)
        } answers {
            text2Mock
        }

        every {
            savedStateHandleMock.get<Int>(MainActivity.ResultExtra.limit)
        } answers {
            20
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun listOk() = runTest {

        mockEnteredValues()

        coEvery {
            useCaseMock.run(any(), any(), any(), any(), any())
        } answers {
            listResultMock.left()
        }

        viewModel = ResultComposableViewModel(
            app = applicationMock,
            computeUseCase = useCaseMock,
            savedStateHandle = savedStateHandleMock
        )

        val values = mutableListOf<ResultUiModel>()
        val job = launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.viewState.collect {
                values.add(it)
            }
        }

        values.last().let {
            Assert.assertTrue(it.items == listResultMock)
        }

        job.cancel()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun numberTooBig() = runTest {

        mockEnteredValues()
        every {
            savedStateHandleMock.get<Int>(MainActivity.ResultExtra.integer1)
        } answers {
            200
        }

        coEvery {
            useCaseMock.run(any(), any(), any(), any(), any())
        } answers {
            UseCaseError.GreaterThanLimitUseCaseError.right()
        }

        viewModel = ResultComposableViewModel(
            app = applicationMock,
            computeUseCase = useCaseMock,
            savedStateHandle = savedStateHandleMock
        )

        val values = mutableListOf<ResultUiModel>()
        val job = launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.viewState.collect {
                values.add(it)
            }
        }

        values.last().let {
            Assert.assertTrue(it.error == UseCaseError.GreaterThanLimitUseCaseError)
        }

        job.cancel()
    }
}