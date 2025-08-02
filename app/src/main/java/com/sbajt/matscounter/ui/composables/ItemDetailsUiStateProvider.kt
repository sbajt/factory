package com.sbajt.matscounter.ui.composables

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.sbajt.matscounter.ui.mappers.toLowerGroupsList
import com.sbajt.matscounter.ui.models.BuildMaterialListUiState
import com.sbajt.matscounter.ui.models.ItemDetailsScreenUiState
import kotlinx.collections.immutable.toImmutableList

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
            itemUiStatList = ItemUiStateProvider.mockItemUiStateList(),
            itemBuildingMaterialList = ItemUiStateProvider.mockBuildingMaterials(
                count = 4,
                lowerGroupTypeList = ItemUiStateProvider.tier1ItemUiState.groupType.toLowerGroupsList()
            ),
            itemBuildMaterialsUiState = BuildMaterialListUiState(
                titleText = "Building materials",
                selectedItemCount = 1,
                buildingMaterialsList = ItemUiStateProvider.tier2ItemUiState.buildingMaterials.toImmutableList(),
            ),
            itemBasicBuildMaterialsUiState = BuildMaterialListUiState(
                titleText = "Basic building materials",
                selectedItemCount = 3,
                buildingMaterialsList = ItemUiStateProvider.mockBuildingMaterials(
                    count = 4,
                    lowerGroupTypeList = ItemUiStateProvider.tier2ItemUiState.groupType.toLowerGroupsList()
                )
            )
        )
    }
}
