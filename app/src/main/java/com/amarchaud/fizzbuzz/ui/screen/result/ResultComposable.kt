package com.amarchaud.fizzbuzz.ui.screen.result

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.amarchaud.fizzbuzz.ui.screen.result.models.ResultUiModel
import com.amarchaud.fizzbuzz.ui.theme.FizzBuzzTheme
import com.amarchaud.fizzbuzz.ui.theme.spacing

@Composable
fun ResultComposable(
    viewModel: ResultComposableViewModel = hiltViewModel(),
) {
    val viewState by viewModel.viewState.collectAsState()
    ResultScreen(
        viewState = viewState
    )
}

@Composable
private fun ResultScreen(
    viewState: ResultUiModel
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .systemBarsPadding()
    ) {
        if (viewState.error == null) {
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.little),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = MaterialTheme.spacing.normal)
            ) {
                itemsIndexed(viewState.items) { index, item ->
                    OneElement(
                        modifier = Modifier,
                        text = item,
                        color = Color.Green
                    )
                    if (index < viewState.items.lastIndex) {
                        Spacer(modifier = Modifier.height(MaterialTheme.spacing.little))
                        Divider(color = Color.Gray, thickness = 1.dp)
                    }
                }
            }
        } else {
            Text(text = "Error !", modifier = Modifier.align(alignment = Alignment.Center))
        }
    }
}

@Composable
private fun OneElement(
    modifier: Modifier = Modifier,
    text: String,
    color: Color
) {
    Text(text = text, style = MaterialTheme.typography.h4.copy(color = color))
}


@Composable
@Preview
private fun PreviewResultScreen() {
    FizzBuzzTheme {
        ResultScreen(
            viewState = ResultUiModel(
                listOf("1", "2", "toto")
            )
        )
    }
}