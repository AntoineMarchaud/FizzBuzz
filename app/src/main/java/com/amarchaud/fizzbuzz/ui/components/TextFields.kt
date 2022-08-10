package com.amarchaud.fizzbuzz.ui.components

import com.amarchaud.fizzbuzz.R
import com.amarchaud.fizzbuzz.ui.components.CustomTextFieldDefaults.iconSize
import com.amarchaud.fizzbuzz.ui.theme.FizzBuzzTheme
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * Use This to create an EditText
 * @param text used to fill the edittext with an initial text
 * @param label used to specify a label to the edittext (aka hint)
 * @param placeHolder used to specify a mask or placeholder
 * @param icon used to add an icon at start
 * @param endIcon used to add an icon at end
 * @param keyboardType used to specify keyboard type (num, text, ...)
 * @param keyboardCapitalization used to specify content capitalization
 * @param isAutoCorrectActivated used to activate autocorrection of content
 * @param maxLines used to define max content lines
 * @param isValid used to show valid state
 * @param isError used to show error state
 * @param isEnabled used to show enable state
 * @param imeAction used to define the edittext ime action (done, next, ...)
 * @param onKeyboardAction used to specify the action to perform on ime action
 * @param onTextChanged used to indicate the action to perform when text change
 * @param onTextFieldFocusChanged used to indicate the action to perform when focus change
 * @param visualTransformation used to add a visual format to the text
 */
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    text: String = "",
    label: String = "",
    placeHolder: String? = null,
    textStyle: TextStyle = MaterialTheme.typography.subtitle2,
    icon: @Composable (() -> Unit)? = null,
    endIcon: @Composable (() -> Unit)? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    keyboardCapitalization: KeyboardCapitalization = KeyboardCapitalization.None,
    isAutoCorrectActivated: Boolean = false,
    maxLines: Int = 1,
    isValid: Boolean = false,
    isError: Boolean = false,
    isEnabled: Boolean = true,
    imeAction: ImeAction = ImeAction.Done,
    onKeyboardAction: (String) -> Unit = {},
    onTextChanged: (String) -> Unit = {},
    onTextFieldFocusChanged: (FocusState, String) -> Unit = { _, _ -> },
    visualTransformation: VisualTransformation = VisualTransformation.None,
    contentDescription: String,
    maxLength: Int = Int.MAX_VALUE,
) {
    val focusManager = LocalFocusManager.current

    CustomTextField(
        modifier = modifier
            .onFocusChanged {
                onTextFieldFocusChanged(it, text)
            },
        value = text,
        onValueChange = { value ->
            with(value) {
                if (this.length <= maxLength) {
                    onTextChanged.invoke(this)
                }
            }
        },
        label = {
            if (label.isNotEmpty()) {
                AnimatedContent(targetState = label) { target ->
                    Text(
                        text = target
                    )
                }
            }
        },
        placeholder = {
            if (placeHolder?.isNotEmpty() == true) {
                AnimatedContent(targetState = placeHolder) { target ->
                    Text(
                        text = target
                    )
                }
            }
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            capitalization = keyboardCapitalization,
            autoCorrect = isAutoCorrectActivated,
            imeAction = imeAction
        ),
        maxLines = maxLines,
        singleLine = maxLines == 1,
        isValid = isValid,
        isError = isError,
        enabled = isEnabled,
        keyboardActions = KeyboardActions(onDone = {
            focusManager.clearFocus()
            onKeyboardAction.invoke(text)
        }, onNext = {
            focusManager.moveFocus(focusDirection = FocusDirection.Down)
            onKeyboardAction.invoke(text)
        }),
        textStyle = textStyle,
        leadingIcon = icon,
        trailingIcon = endIcon,
        visualTransformation = visualTransformation,
        contentDescription = contentDescription,
    )
}


@Composable
@Preview
fun CustomTextFieldWithIconPreview() {
    FizzBuzzTheme {
        Column {
            CustomTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                label = "Champ",
                icon = {
                    Icon(
                        modifier = Modifier.size(iconSize),
                        painter = painterResource(id = R.drawable.ic_launcher_background),
                        contentDescription = "null"
                    )
                },
                contentDescription = ""
            )

        }
    }
}

@Composable
@Preview
fun CustomTextFieldDisabledPreview() {
    FizzBuzzTheme {
        Column {
            CustomTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                label = "Incomes",
                isEnabled = false,
                icon = {
                    Icon(
                        modifier = Modifier.size(iconSize),
                        painter = painterResource(id = R.drawable.ic_launcher_background),
                        contentDescription = "null"
                    )
                },
                contentDescription = ""
            )
            Spacer(modifier = Modifier.height(5.dp))
            CustomTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                label = "Incomes",
                isEnabled = false,
                contentDescription = ""
            )
        }
    }
}