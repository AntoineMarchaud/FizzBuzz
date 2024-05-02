package com.amarchaud.fizzbuzz.presentation

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.amarchaud.fizzbuzz.ui.screen.fields.FieldsComposableViewModel
import com.amarchaud.fizzbuzz.ui.screen.fields.models.FieldsUiModel
import com.amarchaud.fizzbuzz.utils.GenericErrorState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito.mock

class FieldsViewModelTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    private lateinit var viewModel: FieldsComposableViewModel
    private val applicationMock: Application = mock()

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

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun allFieldsOk() = runTest {


        viewModel = FieldsComposableViewModel(
            app = applicationMock,
        )

        viewModel.onUpdateInteger1("3")
        viewModel.onUpdateInteger2("5")
        viewModel.onUpdateText1("fizz")
        viewModel.onUpdateText2("buzz")
        viewModel.onUpdateLimit("20")

        val values = mutableListOf<FieldsUiModel>()
        val job = launch (UnconfinedTestDispatcher(testScheduler)) {
            viewModel.viewState.collect {
                values.add(it)
            }
        }

        values.last().let {
            Assert.assertTrue(it.isEverythingOk)
        }

        job.cancel()
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun oneIntegerTooBig() = runTest {

        viewModel = FieldsComposableViewModel(
            app = applicationMock,
        )

        viewModel.onUpdateInteger1("300")
        viewModel.onUpdateInteger2("5")
        viewModel.onUpdateText1("fizz")
        viewModel.onUpdateText2("buzz")
        viewModel.onUpdateLimit("20")
        viewModel.checkForms()

        val values = mutableListOf<FieldsUiModel>()
        val job = launch (UnconfinedTestDispatcher(testScheduler)) {
            viewModel.viewState.collect {
                values.add(it)
            }
        }

        values.last().let {
            Assert.assertTrue(it.isEverythingOk.not())
            Assert.assertTrue(it.inputs.integer1.state == GenericErrorState.NumberTooBig)
        }

        job.cancel()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun oneFieldEmpty() = runTest {

        viewModel = FieldsComposableViewModel(
            app = applicationMock,
        )

        viewModel.onUpdateInteger1("300")
        viewModel.onUpdateInteger2("5")
        viewModel.onUpdateText1("")
        viewModel.onUpdateText2("buzz")
        viewModel.onUpdateLimit("20")
        viewModel.checkForms()

        val values = mutableListOf<FieldsUiModel>()
        val job = launch (UnconfinedTestDispatcher(testScheduler)) {
            viewModel.viewState.collect {
                values.add(it)
            }
        }

        values.last().let {
            Assert.assertTrue(it.isEverythingOk.not())
            Assert.assertTrue(it.inputs.text1.state == GenericErrorState.Empty)
        }

        job.cancel()
    }
}