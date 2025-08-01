package com.sbajt.matscounter.ui.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
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
    Row(
        modifier = modifier.padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.padding(8.dp),
            color = MaterialTheme.colorScheme.inverseOnSurface,
            text = "Selected item count:"
        )
        NumberPicker(
            value = uiState?.itemCount ?: 1,
            range = 1..20,
            dividersColor = Color.Green,
            textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface),
            onValueChange = {
                onCountChange.invoke(it)
            }
        )
    }
}

@PreviewLightDark
@Composable
private fun InputSectionPreview(@PreviewParameter(InputSectionUiStateProvider::class) uiState: InputSectionUiState) {
    MatsCounterTheme {
        InputSection(
            uiState = uiState,
        )
    }
}

