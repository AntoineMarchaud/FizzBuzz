package com.amarchaud.fizzbuzz.ui.screen.fields

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.amarchaud.fizzbuzz.R
import com.amarchaud.fizzbuzz.ui.screen.fields.models.FieldsUiModel
import com.amarchaud.fizzbuzz.ui.screen.fields.models.isValid
import com.amarchaud.fizzbuzz.utils.GenericErrorState
import com.amarchaud.fizzbuzz.utils.ValidableState
import com.amarchaud.fizzbuzz.utils.takeIfOrElse
import com.amarchaud.fizzbuzz.utils.validate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class FieldsComposableViewModel @Inject constructor(
    app: Application
) : AndroidViewModel(app) {

    private val _integer1 = MutableStateFlow("")
    private val _integer2 = MutableStateFlow("")
    private val _limit = MutableStateFlow("")
    private val _text1 = MutableStateFlow("")
    private val _text2 = MutableStateFlow("")
    private val _showErrors = MutableStateFlow(false)

    val viewState: StateFlow<FieldsUiModel> = combine(
        _integer1,
        _integer2,
        _text1,
        _text2,
        _limit,
        _showErrors
    ) { items ->

        val integer1 = items[0] as String
        val integer2 = items[1] as String
        val text1 = items[2] as String
        val text2 = items[3] as String
        val limit = items[4] as String
        val showErrors = items[5] as Boolean

        val newState = FieldsUiModel().apply {
            this.inputs.apply {
                this.integer1 = this.integer1.copy(
                    value = integer1,
                    state = this.integer1.validator(integer1, limit)
                        .takeIfOrElse(ValidableState.Valid, ValidableState.Neutral),
                    errorId = null
                )
                this.integer2 = this.integer2.copy(
                    value = integer2,
                    state = this.integer2.validator(integer2, limit)
                        .takeIfOrElse(ValidableState.Valid, ValidableState.Neutral),
                    errorId = null
                )
                this.text1 = this.text1.copy(
                    value = text1,
                    state = this.text1.validator(text1, null)
                        .takeIfOrElse(ValidableState.Valid, ValidableState.Neutral),
                    errorId = null
                )
                this.text2 = this.text2.copy(
                    value = text2,
                    state = this.text2.validator(text2, null)
                        .takeIfOrElse(ValidableState.Valid, ValidableState.Neutral),
                    errorId = null
                )
                this.limit = this.limit.copy(
                    value = limit,
                    state = this.limit.validator(limit, null)
                        .takeIfOrElse(ValidableState.Valid, ValidableState.Neutral),
                    errorId = null
                )
            }
        }

        if (showErrors) {
            newState.checkErrors()
        }

        newState.apply {
            this.isEverythingOk = this.inputs.isValid()
        }

        newState
    }.stateIn(viewModelScope, SharingStarted.Eagerly, FieldsUiModel())

    private fun FieldsUiModel.checkErrors() {
        val integer1Valid = this.inputs.integer1.validate(this.inputs.limit.value)
        val integer2Valid = this.inputs.integer2.validate(this.inputs.limit.value)
        val text1Valid = this.inputs.text1.validate()
        val text2Valid = this.inputs.text2.validate()
        val limitValid = this.inputs.limit.validate()

        this.inputs.apply {
            this.integer1 = this.integer1.copy(
                state = integer1Valid,
                errorId = when (integer1Valid) {
                    is GenericErrorState.Empty -> R.string.field_required
                    is GenericErrorState.NumberTooBig -> R.string.number_too_big
                    is GenericErrorState.Syntax -> R.string.not_number
                    else -> null
                }
            )
            this.integer2 = this.integer2.copy(
                state = integer2Valid,
                errorId = when (integer2Valid) {
                    is GenericErrorState.Empty -> R.string.field_required
                    is GenericErrorState.NumberTooBig -> R.string.number_too_big
                    is GenericErrorState.Syntax -> R.string.not_number
                    else -> null
                }
            )
            this.text1 = this.text1.copy(
                state = text1Valid,
                errorId = when (text1Valid) {
                    is GenericErrorState.Empty -> R.string.field_required
                    else -> null
                }
            )
            this.text2 = this.text2.copy(
                state = text2Valid,
                errorId = when (text2Valid) {
                    is GenericErrorState.Empty -> R.string.field_required
                    else -> null
                }
            )
            this.limit = this.limit.copy(
                state = limitValid,
                errorId = when (limitValid) {
                    is GenericErrorState.Empty -> R.string.field_required
                    is GenericErrorState.Syntax -> R.string.not_number
                    else -> null
                }
            )
        }
    }


    fun onUpdateInteger1(value: String) {
        _integer1.value = value
    }

    fun onUpdateInteger2(value: String) {
        _integer2.value = value
    }

    fun onUpdateText1(value: String) {
        _text1.value = value
    }

    fun onUpdateText2(value: String) {
        _text2.value = value
    }

    fun onUpdateLimit(value: String) {
        _limit.value = value
    }

    fun checkForms() {
        if (viewState.value.isEverythingOk.not()) {
            _showErrors.value = true
        }
    }
}