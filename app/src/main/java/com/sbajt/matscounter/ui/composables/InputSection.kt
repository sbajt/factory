package com.sbajt.matscounter.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.chargemap.compose.numberpicker.NumberPicker
import com.sbajt.matscounter.ui.models.InputSectionUiState
import com.sbajt.matscounter.ui.theme.MatsCounterTheme

@Composable
fun InputSection(
    uiState: InputSectionUiState,
    onCountChange: (Int) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .background(MatsCounterTheme.colors.background)
            .padding(MatsCounterTheme.size.contentPadding),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.padding(MatsCounterTheme.size.paddingMedium),
            text = "Selected item count:",
            style = MatsCounterTheme.typography.bodyTextLarge,
            color = MatsCounterTheme.colors.primary,
        )
        var pickerValue by remember { mutableIntStateOf(uiState.itemCount) }
        NumberPicker(
            modifier = Modifier.defaultMinSize(minWidth = 40.dp),
            value = pickerValue,
            range = 1..9,
            textStyle = TextStyle(color = MatsCounterTheme.colors.primary),
            dividersColor = MatsCounterTheme.colors.secondary,
            onValueChange = {
                pickerValue = it
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

