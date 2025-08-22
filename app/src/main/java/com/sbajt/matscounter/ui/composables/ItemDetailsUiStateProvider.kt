package com.sbajt.matscounter.ui.composables

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.sbajt.matscounter.ui.models.BuildMaterialListWrapper
import com.sbajt.matscounter.ui.models.ItemDetailsScreenUiState

class ItemDetailsUiStateProvider : PreviewParameterProvider<ItemDetailsScreenUiState> {

    override val values: Sequence<ItemDetailsScreenUiState> = sequenceOf(
        tier1ItemDetailsScreenUiState,
        tier2ItemDetailsScreenUiState,
    )

    companion object {
        val tier1ItemDetailsScreenUiState = ItemDetailsScreenUiState(
            selectedItem = ItemUiStateProvider.tier2ItemUiState,
            selectedItemAmount = 1,
            selectedItemBuildMaterialListWrapper = BuildMaterialListWrapper(
                titleText = "Build Materials",
                buildMaterialsList = ItemUiStateProvider.mockBuildMaterials()
            )
        )
        val tier2ItemDetailsScreenUiState = ItemDetailsScreenUiState(
            selectedItem = ItemUiStateProvider.tier2ItemUiState,
            selectedItemAmount = 1,
            selectedItemBuildMaterialListWrapper = BuildMaterialListWrapper(
                titleText = "Build materials",
                buildMaterialsList = ItemUiStateProvider.mockBuildMaterials()
            )
        )
    }
}
