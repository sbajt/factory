package com.sbajt.matscounter.ui.composables

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.sbajt.matscounter.ui.models.BuildMaterialListUiState
import com.sbajt.matscounter.ui.models.ItemDetailsScreenUiState

class ItemDetailsUiStateProvider : PreviewParameterProvider<ItemDetailsScreenUiState> {

    override val values: Sequence<ItemDetailsScreenUiState> = sequenceOf(
        emptyItemDetailsScreenUiState,
        defaultItemDetailsScreenUiState,
    )

    companion object {
        val emptyItemDetailsScreenUiState = ItemDetailsScreenUiState()

        val defaultItemDetailsScreenUiState = ItemDetailsScreenUiState(
            selectedItem = ItemUiStateProvider.tier2ItemUiState,
            selectedItemCount = 1,
            selectedItemBuildingMaterialListUiState = BuildMaterialListUiState(
                titleText = "Building Materials",
                buildingMaterialsList = ItemUiStateProvider.tier2ItemUiState.buildingMaterials
            )
        )
    }
}
