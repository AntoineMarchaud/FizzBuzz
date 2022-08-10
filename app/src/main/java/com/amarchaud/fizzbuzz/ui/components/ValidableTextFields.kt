package com.amarchaud.fizzbuzz.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.amarchaud.fizzbuzz.R
import com.amarchaud.fizzbuzz.ui.components.CustomTextFieldDefaults.iconSize
import com.amarchaud.fizzbuzz.ui.theme.FizzBuzzTheme
import com.amarchaud.fizzbuzz.utils.GenericErrorState
import com.amarchaud.fizzbuzz.utils.InputWrapper

@Composable
fun ValidableTextField(
    inputWrapper: InputWrapper,
    modifier: Modifier = Modifier.fillMaxWidth(),
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
    isEnabled: Boolean = true,
    imeAction: ImeAction = ImeAction.Done,
    onKeyboardAction: (String) -> Unit = {},
    onTextChanged: (String) -> Unit = {},
    visualTransformation: VisualTransformation = VisualTransformation.None,
    contentDescription: String
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isValid = inputWrapper.state.isValid()
    val isError = inputWrapper.state.isError()

    Column {
        CustomTextField(
            modifier = modifier,
            text = text,
            label = label,
            placeHolder = placeHolder,
            textStyle = textStyle,
            icon = icon,
            endIcon = endIcon,
            keyboardType = keyboardType,
            keyboardCapitalization = keyboardCapitalization,
            isAutoCorrectActivated = isAutoCorrectActivated,
            maxLines = maxLines,
            isValid = isValid,
            isError = isError,
            isEnabled = isEnabled,
            imeAction = imeAction,
            onKeyboardAction = onKeyboardAction,
            onTextChanged = onTextChanged,
            visualTransformation = visualTransformation,
            contentDescription = contentDescription
        )
        AnimatedVisibility(visible = isError && inputWrapper.errorId != null) {
            inputWrapper.errorId?.let {
                Text(
                    text = stringResource(id = it),
                    style = MaterialTheme.typography.caption,
                    color = CustomTextFieldDefaults.CustomTextFieldColors().indicatorColor(
                        enabled = isEnabled,
                        isValid = isValid,
                        isError = isError,
                        interactionSource = interactionSource
                    ).value,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
@Preview
fun PreviewValidableTextFieldWithIcon() {
    FizzBuzzTheme {
        Column {
            ValidableTextField(
                inputWrapper = InputWrapper(),
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
            Spacer(modifier = Modifier.height(5.dp))
            ValidableTextField(
                inputWrapper = InputWrapper(
                    state = GenericErrorState.Syntax,
                    errorId = R.string.field_required
                ),
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