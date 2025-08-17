package com.sbajt.matscounter.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.sbajt.matscounter.ui.models.BuildMaterialUiState
import com.sbajt.matscounter.ui.theme.MatsCounterTheme

@Composable
fun BuildMaterialView(
    uiState: BuildMaterialUiState,
    selectedItemAmount: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(all = MatsCounterTheme.size.paddingSmall),
    ) {
        Text(
            style = MatsCounterTheme.typography.bodyTextNormal,
            color = MatsCounterTheme.colors.primary,
            text = uiState.name?.let {
                remember { it }
            } ?: "Unknown Material",
        )
        if (uiState.amount > 0) {
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.End,
                style = MatsCounterTheme.typography.bodyTextNormal,
                color = MatsCounterTheme.colors.primary,
                text = if (selectedItemAmount > 0) {
                    "x${uiState.amount * selectedItemAmount}"
                } else {
                    "x${uiState.amount}"
                }
            )
        }
    }
}

@PreviewLightDark
@Composable
fun previewBuildMaterialView(@PreviewParameter(BuildMaterialUiStateProvider::class) uiState: BuildMaterialUiState) {
    MatsCounterTheme {
        BuildMaterialView(
            modifier = Modifier.background(MatsCounterTheme.colors.background),
            uiState = uiState,
            selectedItemAmount = 0,
        )
    }
}
