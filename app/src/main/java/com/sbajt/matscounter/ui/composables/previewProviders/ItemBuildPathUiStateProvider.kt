package com.sbajt.matscounter.ui.composables.previewProviders

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.sbajt.matscounter.ui.composables.previewProviders.BuildMaterialUiStateProvider.Companion.mockItemBuildMaterialListWrapperList
import com.sbajt.matscounter.ui.models.appBars.AppBarState
import com.sbajt.matscounter.ui.models.screens.ItemBuildPathScreenUiState

class ItemBuildPathUiStateProvider : PreviewParameterProvider<ItemBuildPathScreenUiState> {

    override val values: Sequence<ItemBuildPathScreenUiState> = sequenceOf(
        defaultBuildPathUiState,
        tier2BuildPathUiState,
        tier3BuildPathUiState,
        tier4BuildPathUiState,
    )

    companion object {

        val tier1Item = ItemUiStateProvider.tier1ItemUiState
        val defaultBuildPathUiState: ItemBuildPathScreenUiState =
            ItemBuildPathScreenUiState.Content(
                selectedItem = tier1Item,
                selectedItemAmount = 1,
                selectedItemBuildMaterialListWrapperList = mockItemBuildMaterialListWrapperList(
                    selectedItemGroupType = tier1Item.groupType
                ),
                appBarState = AppBarState.ItemBuildPathAppBar(
                    title = "Build path",
                    actionList = emptyList()
                )
            )

        val tier2Item = ItemUiStateProvider.tier2ItemUiState
        val tier2BuildPathUiState: ItemBuildPathScreenUiState =
            ItemBuildPathScreenUiState.Content(
                selectedItem = tier2Item,
                selectedItemAmount = 1,
                selectedItemBuildMaterialListWrapperList = mockItemBuildMaterialListWrapperList(
                    selectedItemGroupType = tier2Item.groupType
                ),
                appBarState = AppBarState.ItemBuildPathAppBar(
                    title = "Build path",
                    actionList = emptyList()
                )
            )
        val tier3Item = ItemUiStateProvider.tier3ItemUiState
        val tier3BuildPathUiState: ItemBuildPathScreenUiState =
            ItemBuildPathScreenUiState.Content(
                selectedItem = tier3Item,
                selectedItemAmount = 1,
                selectedItemBuildMaterialListWrapperList = mockItemBuildMaterialListWrapperList(
                    selectedItemGroupType = tier3Item.groupType
                ),
                appBarState = AppBarState.ItemBuildPathAppBar(
                    title = "Build path",
                    actionList = emptyList()
                )
            )
        val tier4Item = ItemUiStateProvider.tier4ItemUiState
        val tier4BuildPathUiState: ItemBuildPathScreenUiState =
            ItemBuildPathScreenUiState.Content(
                selectedItem = tier4Item,
                selectedItemAmount = 1,
                selectedItemBuildMaterialListWrapperList = mockItemBuildMaterialListWrapperList(
                    selectedItemGroupType = tier4Item.groupType
                ),
                appBarState = AppBarState.ItemBuildPathAppBar(
                    title = "Build path",
                    actionList = emptyList()
                )
            )

    }
}

