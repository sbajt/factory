package com.sbajt.matscounter.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
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
        NumberPicker(
            value = uiState?.itemCount ?: 1,
            range = 1..20,
            textStyle = MatsCounterTheme.typography.bodyTextNormal.copy(color = MatsCounterTheme.colors.primary),
            dividersColor = MatsCounterTheme.colors.primary,
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

