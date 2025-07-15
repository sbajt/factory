package com.sbajt.matscounter.ui.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
        TextField(
            modifier = modifier,
            label = {
                Text(
                    text = uiState?.selectedItem?.name?.let {
                        "${it} count"
                    } ?: ""
                )
            },
            value = uiState?.itemCount.toString(),
            onValueChange = { newItemCount ->
                onCountChange.invoke(newItemCount.trim().toInt())
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun InputSectionPreview() {
    MatsCounterTheme {
        InputSection(
            uiState = mockInputSectionUiState(),
        )
    }
}

fun mockInputSectionUiState() = InputSectionUiState(
    itemCount = 1,
    selectedItem = mockItemUiState()
)

