package com.sbajt.matscounter.ui.composables

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.sbajt.matscounter.ui.models.BuildMaterialListUiState
import kotlinx.collections.immutable.persistentListOf

class BuildMaterialListUiStateProvider : PreviewParameterProvider<BuildMaterialListUiState> {

    override val values: Sequence<BuildMaterialListUiState> = sequenceOf(
        emptyMaterialList,
        defaultMaterialList,
    )

    companion object {

        val emptyMaterialList = BuildMaterialListUiState()

        val defaultMaterialList = BuildMaterialListUiState(
            titleText = "Building Materials",
            buildingMaterialsList = persistentListOf(
                BuildMaterialUiStateProvider.defaultBuildingMaterialUiState,
                BuildMaterialUiStateProvider.basicMaterialUiState,
                BuildMaterialUiStateProvider.basicMaterial2UiState
            ),
            selectedItemCount = 1
        )
    }
}
