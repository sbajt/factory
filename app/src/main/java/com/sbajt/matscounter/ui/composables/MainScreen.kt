package com.sbajt.matscounter.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sbajt.matscounter.ui.models.InputScreenUiState
import com.sbajt.matscounter.ui.models.MainUiState
import com.sbajt.matscounter.ui.theme.MatsCounterTheme

@Composable
fun MainScreen(
    uiState: MainUiState,
    modifier: Modifier = Modifier
) {
    Scaffold { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            InputScreenPart(
                uiState = uiState.inputScreenUiState,
            )
        }
    }
}

@Composable
private fun InputScreenPart(
    uiState: InputScreenUiState?,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        TextField(
            modifier = modifier,
            label = {
                Text(text = remember {
                    mutableStateOf(uiState?.selectedItem?.name)
                }.value.toString())
            },
            value = uiState?.itemCount.toString(),
            onValueChange = { newItemCount ->

            }
        )
        Text(
            text = "Count: ${uiState?.itemCount}",
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MatsCounterTheme {
        MainScreen(
            uiState = MainUiState(
                inputScreenUiState = InputScreenUiState(
                    selectedItem = null,
                    itemCount = 0,
                ),
            )
        )
    }
}
