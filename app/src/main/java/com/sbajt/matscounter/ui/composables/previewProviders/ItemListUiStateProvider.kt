package com.sbajt.matscounter.ui.composables.previewProviders

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.sbajt.matscounter.ui.models.ItemGroupType
import com.sbajt.matscounter.ui.models.screens.ItemListScreenUiState
import com.sbajt.matscounter.ui.models.views.ItemUiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList

class ItemListUiStateProvider : PreviewParameterProvider<ItemListScreenUiState> {

    override val values: Sequence<ItemListScreenUiState> = sequenceOf(
        defaultGridSectionUiState,
        basicMaterialsGridSectionUiState,
        tier1GridSectionUiState,
    )

    companion object {

        val defaultGridSectionUiState = ItemListScreenUiState.Content(itemUiStateList = ItemUiStateProvider.mockItemUiStateList())

        val basicMaterialsGridSectionUiState = ItemListScreenUiState.Content(
            itemUiStateList = ItemUiStateProvider.mockItemUiStateList(6)
                .map {
                    it.copy(groupType = ItemGroupType.BASIC_MATERIAL)
                }.toPersistentList()
        )

        val tier1GridSectionUiState = ItemListScreenUiState.Content(
            itemUiStateList = ItemUiStateProvider.mockItemUiStateList(12)
                .map {
                    it.copy(groupType = ItemGroupType.TIER1)
                }.toPersistentList()
        )
    }
}
