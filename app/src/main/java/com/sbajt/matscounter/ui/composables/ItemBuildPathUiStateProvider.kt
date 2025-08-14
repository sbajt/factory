package com.sbajt.matscounter.ui.composables

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.sbajt.matscounter.ui.models.BuildMaterialListWrapper
import com.sbajt.matscounter.ui.models.ItemBuildPathUiState
import kotlinx.collections.immutable.toPersistentList

class ItemBuildPathUiStateProvider : PreviewParameterProvider<ItemBuildPathUiState> {

    override val values: Sequence<ItemBuildPathUiState> = sequenceOf(
        defaultBuildPathUiState
    )

    companion object {

        val defaultBuildPathUiState: ItemBuildPathUiState = ItemBuildPathUiState(
            selectedItem = ItemUiStateProvider.tier1ItemUiState,
            buildPathList = List(3) { index ->
                BuildMaterialListWrapper(
                    titleText = ItemUiStateProvider.tier1ItemUiState.groupType.name,
                    buildMaterialsList = ItemUiStateProvider.mockBuildMaterials(
                        count = 3
                    ).toPersistentList()
                )
            }
        )
    }
}
