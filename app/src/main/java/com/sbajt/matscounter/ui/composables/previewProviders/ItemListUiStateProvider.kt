package com.sbajt.matscounter.ui.composables.previewProviders

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.sbajt.matscounter.ui.models.ItemGroupType
import com.sbajt.matscounter.ui.models.views.ItemUiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList

class ItemListUiStateProvider : PreviewParameterProvider<ImmutableList<ItemUiState>> {

    override val values: Sequence<ImmutableList<ItemUiState>> = sequenceOf(
        defaultGridSectionUiState,
        basicMaterialsGridSectionUiState,
        tier1GridSectionUiState,
    )

    companion object {

        val defaultGridSectionUiState: ImmutableList<ItemUiState> = ItemUiStateProvider.Companion.mockItemUiStateList()

        val basicMaterialsGridSectionUiState: ImmutableList<ItemUiState> = ItemUiStateProvider.Companion.mockItemUiStateList(6)
            .map {
                it.copy(groupType = ItemGroupType.BASIC_MATERIAL)
            }.toPersistentList()

        val tier1GridSectionUiState: ImmutableList<ItemUiState> = ItemUiStateProvider.Companion.mockItemUiStateList(12)
            .map {
                it.copy(groupType = ItemGroupType.TIER1)
            }.toPersistentList()
    }
}
