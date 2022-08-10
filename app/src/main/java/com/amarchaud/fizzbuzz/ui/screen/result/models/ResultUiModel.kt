package com.amarchaud.fizzbuzz.ui.screen.result.models

import com.amarchaud.fizzbuzz.domain.usecase.errors.UseCaseError

data class ResultUiModel(
    val items: List<String> = emptyList(),
    val error: UseCaseError? = null
)