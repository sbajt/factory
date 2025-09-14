package com.sbajt.matscounter.ui.composables.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.sbajt.matscounter.ui.composables.previewProviders.BuildMaterialUiStateProvider
import com.sbajt.matscounter.ui.models.views.BuildMaterialUiState
import com.sbajt.matscounter.ui.theme.FactoryTheme

@Composable
fun BuildMaterialView(
    uiState: BuildMaterialUiState,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(FactoryTheme.colors.background)
            .padding(all = FactoryTheme.dimensions.small),
    ) {
        val name by remember { mutableStateOf(uiState.name ?: "") }
        Text(
            style = FactoryTheme.typography.bodyTextNormal,
            color = FactoryTheme.colors.primary,
            text = name
        )
        Text(
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.End,
            style = FactoryTheme.typography.bodyTextNormal,
            color = FactoryTheme.colors.primary,
            text = uiState.amount.toString()
        )
    }
}

@PreviewLightDark
@Composable
fun previewBuildMaterialView(@PreviewParameter(BuildMaterialUiStateProvider::class) uiState: BuildMaterialUiState) {
    FactoryTheme {
        BuildMaterialView(
            uiState = uiState,
        )
    }
}
