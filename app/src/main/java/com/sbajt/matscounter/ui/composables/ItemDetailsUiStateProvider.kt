package com.sbajt.matscounter.ui.composables

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.sbajt.matscounter.ui.models.BuildMaterialListWrapper
import com.sbajt.matscounter.ui.models.ItemDetailsScreenUiState

class ItemDetailsUiStateProvider : PreviewParameterProvider<ItemDetailsScreenUiState> {

    override val values: Sequence<ItemDetailsScreenUiState> = sequenceOf(
        emptyItemDetailsScreenUiState,
        tier1ItemDetailsScreenUiState,
        tier2ItemDetailsScreenUiState,
    )

    companion object {
        val emptyItemDetailsScreenUiState = ItemDetailsScreenUiState()

        val tier1ItemDetailsScreenUiState = ItemDetailsScreenUiState(
            selectedItem = ItemUiStateProvider.tier2ItemUiState,
            selectedItemAmount = 1,
            selectedItemBuildMaterialListWrapper = BuildMaterialListWrapper(
                titleText = "Building Materials",
                subtitleText = "Basic materials",
                buildingMaterialsList = ItemUiStateProvider.mockBuildingMaterials()
            )
        )
        val tier2ItemDetailsScreenUiState = ItemDetailsScreenUiState(
            selectedItem = ItemUiStateProvider.tier2ItemUiState,
            selectedItemAmount = 1,
            selectedItemBuildMaterialListWrapper = BuildMaterialListWrapper(
                titleText = "Build materials",
                subtitleText = "Tier 1",
                buildingMaterialsList = ItemUiStateProvider.mockBuildingMaterials()
            )
        )
    }
}
