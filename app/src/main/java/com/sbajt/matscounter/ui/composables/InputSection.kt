package com.sbajt.matscounter.ui.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sbajt.matscounter.ui.models.InputSectionUiState
import com.sbajt.matscounter.ui.theme.MatsCounterTheme

@Composable
fun InputSection(
    uiState: InputSectionUiState?,
    onCountChange: (Int) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.padding(16.dp)
    ) {
        if (uiState?.selectedItem != null) {
            TextField(
                modifier = modifier,
                label = {
                    Text(text = "${uiState.selectedItem.name} count")
                },
                value = remember { uiState.itemCount.toString() },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                onValueChange = { newItemCount ->
                    val filteredInput = newItemCount.filter { it.isDigit() }
                    val newCount = filteredInput.toIntOrNull() ?: 0
                    if (newCount >= 0) {
                        onCountChange.invoke(newCount)
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun InputSectionPreview() {
    MatsCounterTheme {
        InputSection(
            uiState = mockInputSectionUiState(),
        )
    }
}

private fun mockInputSectionUiState() = InputSectionUiState(
    itemCount = 1,
    selectedItem = mockItemUiState()
)

