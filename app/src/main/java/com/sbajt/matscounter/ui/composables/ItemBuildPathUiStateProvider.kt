package com.sbajt.matscounter.ui.composables

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.sbajt.matscounter.ui.mappers.toLowerGroupsList
import com.sbajt.matscounter.ui.models.BuildMaterialListUiState
import com.sbajt.matscounter.ui.models.ItemBuildPathUiState

class ItemBuildPathUiStateProvider : PreviewParameterProvider<ItemBuildPathUiState> {

    override val values: Sequence<ItemBuildPathUiState> = sequenceOf(
        defaultBuildPathUiState
    )

    companion object {

        val defaultBuildPathUiState: ItemBuildPathUiState = ItemBuildPathUiState(
            selectedItem = ItemUiStateProvider.tier1ItemUiState,
            buildPathList = listOf(
                BuildMaterialListUiState(
                    titleText = ItemUiStateProvider.tier1ItemUiState.groupType.name,
                    selectedItemCount = 1,
                    buildingMaterialsList = ItemUiStateProvider.mockBuildingMaterials(
                        count = 3,
                        lowerGroupTypeList = ItemUiStateProvider.tier1ItemUiState.groupType.toLowerGroupsList()
                    )
                )
            )
        )
    }
}
