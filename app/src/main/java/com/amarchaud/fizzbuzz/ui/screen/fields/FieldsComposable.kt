package com.amarchaud.fizzbuzz.ui.screen.fields

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.amarchaud.fizzbuzz.ui.components.ValidableTextField
import com.amarchaud.fizzbuzz.ui.screen.fields.models.FieldsUiModel
import com.amarchaud.fizzbuzz.ui.theme.FizzBuzzTheme
import com.amarchaud.fizzbuzz.ui.theme.spacing
import com.amarchaud.fizzbuzz.R

@Composable
fun FieldsComposable(
    viewModel: FieldsComposableViewModel = hiltViewModel(),
    onNextScreen: (Int, Int, String, String, Int) -> Unit,
) {
    BackHandler {}

    val viewState by viewModel.viewState.collectAsState()
    FieldsScreen(
        viewState = viewState,
        onUpdateInteger1 = viewModel::onUpdateInteger1,
        onUpdateInteger2 = viewModel::onUpdateInteger2,
        onUpdateText1 = viewModel::onUpdateText1,
        onUpdateText2 = viewModel::onUpdateText2,
        onUpdateLimit = viewModel::onUpdateLimit,
        onButtonClicked = {
            viewModel.checkForms()
            if (viewState.isEverythingOk) {
                with(viewState.inputs) {
                    onNextScreen(
                        this.integer1.value.toInt(),
                        this.integer2.value.toInt(),
                        this.text1.value,
                        this.text2.value,
                        this.limit.value.toInt()
                    )
                }
            }
        }
    )
}

@Composable
private fun FieldsScreen(
    viewState: FieldsUiModel,
    onUpdateInteger1: (String) -> Unit,
    onUpdateInteger2: (String) -> Unit,
    onUpdateText1: (String) -> Unit,
    onUpdateText2: (String) -> Unit,
    onUpdateLimit: (String) -> Unit,
    onButtonClicked: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MaterialTheme.spacing.normal)
                .padding(bottom = MaterialTheme.spacing.normal),
            backgroundColor = Color.White,
            shape = MaterialTheme.shapes.medium.copy(all = CornerSize(12.dp)),
            elevation = 12.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MaterialTheme.spacing.normal),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                ValidableTextField(
                    label = "Enter integer 1",
                    text = viewState.inputs.integer1.value,
                    inputWrapper = viewState.inputs.integer1,
                    contentDescription = "integer1",
                    onTextChanged = onUpdateInteger1,
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                )

                Spacer(modifier = Modifier.height(MaterialTheme.spacing.normal))

                ValidableTextField(
                    label = "Enter integer 2",
                    text = viewState.inputs.integer2.value,
                    inputWrapper = viewState.inputs.integer2,
                    contentDescription = "integer2",
                    onTextChanged = onUpdateInteger2,
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                )

                Spacer(modifier = Modifier.height(MaterialTheme.spacing.normal))

                ValidableTextField(
                    label = "Enter text 1",
                    text = viewState.inputs.text1.value,
                    inputWrapper = viewState.inputs.text1,
                    contentDescription = "text1",
                    onTextChanged = onUpdateText1,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                )

                Spacer(modifier = Modifier.height(MaterialTheme.spacing.normal))

                ValidableTextField(
                    label = "Enter text 2",
                    text = viewState.inputs.text2.value,
                    inputWrapper = viewState.inputs.text2,
                    contentDescription = "text2",
                    onTextChanged = onUpdateText2,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                )

                Spacer(modifier = Modifier.height(MaterialTheme.spacing.normal))

                ValidableTextField(
                    label = "Enter limit",
                    text = viewState.inputs.limit.value,
                    inputWrapper = viewState.inputs.limit,
                    contentDescription = "limit",
                    onTextChanged = onUpdateLimit,
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                )

                Spacer(modifier = Modifier.height(MaterialTheme.spacing.normal))

                Button(onClick = onButtonClicked) {
                    Text(text = stringResource(id = R.string.next_screen))
                }
            }
        }
    }

}

@Composable
@Preview
private fun PreviewFieldsComposable() {
    FizzBuzzTheme {
        FieldsScreen(viewState = FieldsUiModel(),
            onUpdateInteger1 = {},
            onUpdateInteger2 = {},
            onUpdateText1 = {},
            onUpdateText2 = {},
            onButtonClicked = {},
            onUpdateLimit = {})
    }
}