package com.amarchaud.fizztext2Mock.presentation

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.amarchaud.fizzbuzz.domain.usecase.GetFizzBuzzUseCase
import com.amarchaud.fizzbuzz.domain.usecase.errors.UseCaseError
import com.amarchaud.fizzbuzz.ui.MainActivity
import com.amarchaud.fizzbuzz.ui.screen.result.ResultComposableViewModel
import com.amarchaud.fizzbuzz.ui.screen.result.models.ResultUiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.rules.TestRule
import org.mockito.Mockito.mock
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever

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
    private val applicationMock: Application = mock()
    private val useCaseMock: GetFizzBuzzUseCase = mock()
    private val savedStateHandleMock: SavedStateHandle = mock()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun mockEnteredValues() {
        whenever(savedStateHandleMock.get<Int>(MainActivity.ResultExtra.integer1))
            .thenReturn(2)

        whenever(savedStateHandleMock.get<Int>(MainActivity.ResultExtra.integer2))
            .thenReturn(6)

        whenever(savedStateHandleMock.get<String>(MainActivity.ResultExtra.text1))
            .thenReturn(text1Mock)

        whenever(savedStateHandleMock.get<String>(MainActivity.ResultExtra.text2))
            .thenReturn(text2Mock)

        whenever(savedStateHandleMock.get<Int>(MainActivity.ResultExtra.limit))
            .thenReturn(20)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun listOk() = runTest {

        mockEnteredValues()

        whenever(useCaseMock.run(any(), any(), any(), any(), any()))
            .thenReturn(Result.success(listResultMock))

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

        whenever(savedStateHandleMock.get<Int>(MainActivity.ResultExtra.integer1))
            .thenReturn(200)

        whenever(useCaseMock.run(any(), any(), any(), any(), any()))
            .thenReturn(Result.failure(UseCaseError.GreaterThanLimitUseCaseError()))

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
            Assert.assertEquals(it.error?.javaClass, UseCaseError.GreaterThanLimitUseCaseError().javaClass)
        }

        job.cancel()
    }
}