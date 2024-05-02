package com.amarchaud.fizzbuzz.ui.screen.result

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.amarchaud.fizzbuzz.domain.usecase.GetFizzBuzzUseCase
import com.amarchaud.fizzbuzz.domain.usecase.errors.UseCaseError
import com.amarchaud.fizzbuzz.ui.MainActivity
import com.amarchaud.fizzbuzz.ui.screen.result.models.ResultUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResultComposableViewModel @Inject constructor(
    app: Application,
    savedStateHandle: SavedStateHandle,
    computeUseCase: GetFizzBuzzUseCase
) : AndroidViewModel(app) {

    private val _res = MutableStateFlow<List<String>>(emptyList())
    private val _error = MutableStateFlow<UseCaseError?>(null)

    val viewState: StateFlow<ResultUiModel> = combine(
        _res,
        _error,
    ) { items ->
        val res = items[0] as List<String>
        val error = items[1] as UseCaseError?
        ResultUiModel(
            error = error,
            items = res
        )
    }.stateIn(viewModelScope, SharingStarted.Eagerly, ResultUiModel())

    init {
        viewModelScope.launch {
            with(savedStateHandle) {
                computeUseCase.run(
                    int1 = get<Int>(MainActivity.ResultExtra.integer1) ?: 0,
                    int2 = get<Int>(MainActivity.ResultExtra.integer2) ?: 0,
                    text1 = get<String>(MainActivity.ResultExtra.text1).orEmpty(),
                    text2 = get<String>(MainActivity.ResultExtra.text2).orEmpty(),
                    limit = get<Int>(MainActivity.ResultExtra.limit) ?: 0,
                ).onSuccess {
                    _res.value = it
                }.onFailure {
                    _error.value = it as UseCaseError
                }
            }

        }
    }
}