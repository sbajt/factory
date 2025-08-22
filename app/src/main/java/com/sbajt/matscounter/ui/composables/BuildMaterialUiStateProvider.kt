package com.sbajt.matscounter.ui.composables

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.sbajt.matscounter.ui.models.BuildMaterialUiState

class BuildMaterialUiStateProvider : PreviewParameterProvider<BuildMaterialUiState> {

    override val values: Sequence<BuildMaterialUiState> = sequenceOf(
        basicMaterialUiState,
        materialTier1UiState,
        materialTier2UiState,
        materialTier3UiState,
        materialTier4UiState,

    )

    companion object {

        val basicMaterialUiState = BuildMaterialUiState(
            name = "Basic Material",
            amount = 5,
        )
        val materialTier1UiState = BuildMaterialUiState(
            name = "Material Tier 1",
            amount = 5,
        )
        val materialTier2UiState = BuildMaterialUiState(
            name = "Material Tier 2",
            amount = 5,
        )
        val materialTier3UiState = BuildMaterialUiState(
            name = "Material Tier 3",
            amount = 5,
        )
        val materialTier4UiState = BuildMaterialUiState(
            name = "Material Tier 4",
            amount = 5,
        )
    }
}
