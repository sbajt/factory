package com.sbajt.matscounter.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.sbajt.matscounter.ui.composables.previewProviders.BuildMaterialUiStateProvider
import com.sbajt.matscounter.ui.models.views.BuildMaterialUiState
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
        val materialName by remember { mutableStateOf(uiState.name ?: "") }
        Text(
            style = MatsCounterTheme.typography.bodyTextNormal,
            color = MatsCounterTheme.colors.primary,
            text = materialName
        )
        val amount by remember { mutableIntStateOf(uiState.amount) }
        Text(
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.End,
            style = MatsCounterTheme.typography.bodyTextNormal,
            color = MatsCounterTheme.colors.primary,
            text = (amount * selectedItemAmount).toString()
        )
    }
}

@PreviewLightDark
@Composable
fun previewBuildMaterialView(@PreviewParameter(BuildMaterialUiStateProvider::class) uiState: BuildMaterialUiState) {
    MatsCounterTheme {
        BuildMaterialView(
            modifier = Modifier.background(MatsCounterTheme.colors.background),
            uiState = uiState,
            selectedItemAmount = 1,
        )
    }
}
