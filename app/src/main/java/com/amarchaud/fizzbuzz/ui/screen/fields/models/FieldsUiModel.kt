package com.amarchaud.fizzbuzz.ui.screen.fields.models

import com.amarchaud.fizzbuzz.utils.InputWrapper
import com.amarchaud.fizzbuzz.utils.integerValidatorWithMax
import com.amarchaud.fizzbuzz.utils.textValidator

data class FieldsUiModel(
    val inputs: FieldsInputs = FieldsInputs(),
    var isEverythingOk: Boolean = false
)

fun FieldsInputs.isValid(): Boolean {
    return integer1.state.isValid() && integer2.state.isValid() && text1.state.isValid() && text2.state.isValid() && limit.state.isValid()
}

data class FieldsInputs(
    var integer1: InputWrapper = InputWrapper(validator = { value, param ->
        integerValidatorWithMax(value, param?.toIntOrNull())
    }),
    var integer2: InputWrapper = InputWrapper(validator = { value, param ->
        integerValidatorWithMax(value, param?.toIntOrNull())
    }),
    var text1: InputWrapper = InputWrapper(validator = { value, _ -> textValidator(value) }),
    var text2: InputWrapper = InputWrapper(validator = { value, _ -> textValidator(value) }),
    var limit: InputWrapper = InputWrapper(validator = { value, _ -> integerValidatorWithMax(value) })
)