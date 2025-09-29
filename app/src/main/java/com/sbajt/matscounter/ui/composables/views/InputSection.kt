package com.sbajt.matscounter.ui.composables.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
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
import com.chargemap.compose.numberpicker.NumberPicker
import com.sbajt.matscounter.ui.composables.previewProviders.InputSectionUiStateProvider
import com.sbajt.matscounter.ui.models.views.InputSectionUiState
import com.sbajt.matscounter.ui.theme.FactoryTheme

private val inputSectionRange = 1..10

@Composable
fun InputSection(
    uiState: InputSectionUiState,
    onCountChange: (Int) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .background(FactoryTheme.colors.background)
            .padding(FactoryTheme.dimensions.contentPadding),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.padding(FactoryTheme.dimensions.medium),
            text = "Item count:",
            style = FactoryTheme.typography.bodyTextLarge,
            color = FactoryTheme.colors.primary,
        )
        var pickerValue by remember { mutableIntStateOf(uiState.itemCount) }
        NumberPicker(
            value = pickerValue,
            range = inputSectionRange,
            textStyle = TextStyle(color = FactoryTheme.colors.primary),
            dividersColor = FactoryTheme.colors.secondary,
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
    FactoryTheme {
        InputSection(
            uiState = uiState,
        )
    }
}

