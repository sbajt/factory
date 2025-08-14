package com.sbajt.matscounter.ui.composables

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.sbajt.matscounter.ui.models.BuildMaterialUiState

class BuildMaterialUiStateProvider : PreviewParameterProvider<BuildMaterialUiState> {

    override val values: Sequence<BuildMaterialUiState> = sequenceOf(
        emptyBuildMaterialUiState,
        defaultBuildMaterialUiState,
        basicMaterialUiState,
        basicMaterial2UiState
    )

    companion object {
        val emptyBuildMaterialUiState = BuildMaterialUiState(
            name = null,
            amount = 0,
        )

        val defaultBuildMaterialUiState = BuildMaterialUiState(
            name = "Default Material",
            amount = 1,
        )

        val basicMaterialUiState = BuildMaterialUiState(
            name = "Basic Material",
            amount = 5,
        )
        val basicMaterial2UiState = BuildMaterialUiState(
            name = "Basic Material",
            amount = 5,
        )
    }
}
