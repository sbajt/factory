package com.sbajt.matscounter.ui.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chargemap.compose.numberpicker.NumberPicker
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
        NumberPicker(
            value = uiState?.itemCount ?: 1,
            range = 1..20,
            dividersColor = Color.Green,
            textStyle = TextStyle(color = Color.Cyan),
            onValueChange = {
                onCountChange.invoke(it)
            }
        )
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

