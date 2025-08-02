package com.sbajt.matscounter.ui.composables

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.sbajt.matscounter.ui.models.ItemBuildPathUiState
import com.sbajt.matscounter.ui.models.ItemGroupType
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentMap

class ItemBuildPathUiStateProvider : PreviewParameterProvider<ItemBuildPathUiState> {

    override val values: Sequence<ItemBuildPathUiState> = sequenceOf(
        defaultBuildPathUiState
    )

    companion object {

        val defaultBuildPathUiState: ItemBuildPathUiState = ItemBuildPathUiState(
            selectedItem = ItemUiStateProvider.tier1ItemUiState,
            buildPath = mapOf(
                ItemGroupType.TIER1 to persistentListOf(
                    BuildMaterialUiStateProvider.defaultBuildingMaterialUiState
                ),
                ItemGroupType.TIER2 to persistentListOf(
                    BuildMaterialUiStateProvider.defaultBuildingMaterialUiState
                ),
                ItemGroupType.TIER3 to persistentListOf(
                    BuildMaterialUiStateProvider.defaultBuildingMaterialUiState
                ),
            ).toPersistentMap(),
            buildMaterialListUiState = BuildMaterialListUiStateProvider.defaultMaterialList
        )
    }

}
