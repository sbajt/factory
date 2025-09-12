package com.sbajt.matscounter.ui.composables.previewProviders

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.sbajt.matscounter.ui.composables.previewProviders.BuildMaterialUiStateProvider.Companion.mockItemBuildMaterialListWrapperList
import com.sbajt.matscounter.ui.models.screens.ItemBuildPathScreenUiState

class ItemBuildPathUiStateProvider : PreviewParameterProvider<ItemBuildPathScreenUiState> {

    override val values: Sequence<ItemBuildPathScreenUiState> = sequenceOf(
        defaultBuildPathUiState
    )

    companion object {

        val defaultBuildPathUiState: ItemBuildPathScreenUiState =
            ItemBuildPathScreenUiState.Content(
                selectedItem = ItemUiStateProvider.Companion.tier1ItemUiState,
                selectedItemAmount = 1,
                selectedItemBuildMaterialListWrapperList = mockItemBuildMaterialListWrapperList(
                    selectedItemGroupType = ItemUiStateProvider.Companion.tier1ItemUiState.groupType
                )
            )
    }
}

