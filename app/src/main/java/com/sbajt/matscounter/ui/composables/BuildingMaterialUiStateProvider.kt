package com.sbajt.matscounter.ui.composables

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.sbajt.matscounter.ui.models.BuildingMaterialUiState

class BuildingMaterialUiStateProvider : PreviewParameterProvider<BuildingMaterialUiState> {

    override val values: Sequence<BuildingMaterialUiState> = sequenceOf(
        emptyBuildingMaterialUiState,
        defaultBuildingMaterialUiState,
        basicMaterialUiState,
        basicMaterial2UiState
    )

    companion object {
        val emptyBuildingMaterialUiState = BuildingMaterialUiState(
            name = null,
            count = 0,
        )

        val defaultBuildingMaterialUiState = BuildingMaterialUiState(
            name = "Default Material",
            count = 1,
        )

        val basicMaterialUiState = BuildingMaterialUiState(
            name = "Basic Material",
            count = 5,
        )
        val basicMaterial2UiState = BuildingMaterialUiState(
            name = "Basic Material",
            count = 5,
        )
    }
}
