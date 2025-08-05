package com.sbajt.matscounter.ui.composables

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.sbajt.matscounter.ui.models.BuildMaterialListWrapper
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
            selectedItemAmount = 1,
            selectedItemBuildMaterialListWrapper = BuildMaterialListWrapper(
                titleText = "Building Materials",
                buildingMaterialsList = ItemUiStateProvider.mockBuildingMaterials()
            )
        )
    }
}
