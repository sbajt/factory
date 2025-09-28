package com.sbajt.matscounter.ui.composables.previewProviders

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.sbajt.matscounter.ui.composables.previewProviders.ItemListUiStateProvider.Companion.mockedAppBarState
import com.sbajt.matscounter.ui.models.ItemGroupType
import com.sbajt.matscounter.ui.models.appBars.AppBarState
import com.sbajt.matscounter.ui.models.screens.ItemListScreenUiState
import kotlinx.collections.immutable.toPersistentList

class ItemListUiStateProvider : PreviewParameterProvider<ItemListScreenUiState> {

    override val values: Sequence<ItemListScreenUiState> = sequenceOf(
        defaultGridSectionUiState,
        basicMaterialsGridSectionUiState,
        tier1GridSectionUiState,
    )

    companion object {

        val mockedAppBarState = AppBarState.ItemList(
            title = "Item list",
            actionList = emptyList()
        )

        val defaultGridSectionUiState = ItemListScreenUiState.Content(
            appBarState = mockedAppBarState,
            itemUiStateList = ItemUiStateProvider.mockItemUiStateList(),
        )

        val basicMaterialsGridSectionUiState = ItemListScreenUiState.Content(
            appBarState = mockedAppBarState,
            itemUiStateList = ItemUiStateProvider.mockItemUiStateList(6)
                .map {
                    it.copy(groupType = ItemGroupType.BASIC_MATERIAL)
                }.toPersistentList(),
        )

        val tier1GridSectionUiState = ItemListScreenUiState.Content(
            appBarState = mockedAppBarState,
            itemUiStateList = ItemUiStateProvider.mockItemUiStateList(12)
                .map {
                    it.copy(groupType = ItemGroupType.TIER1)
                }.toPersistentList(),
        )
    }
}
