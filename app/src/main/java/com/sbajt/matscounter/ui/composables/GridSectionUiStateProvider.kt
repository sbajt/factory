package com.sbajt.matscounter.ui.composables

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.sbajt.matscounter.ui.models.ItemGroupType
import com.sbajt.matscounter.ui.models.ItemUiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList

class GridSectionUiStateProvider : PreviewParameterProvider<ImmutableList<ItemUiState>> {

    override val values: Sequence<ImmutableList<ItemUiState>> = sequenceOf(
        defaultGridSectionUiState,
        multipleGroupTypeGridSectionUiState
    )

    companion object {

        val defaultGridSectionUiState: ImmutableList<ItemUiState> = ItemUiStateProvider.mockItemUiStateList()

        val multipleGroupTypeGridSectionUiState: ImmutableList<ItemUiState> = ItemUiStateProvider.mockItemUiStateList(50)
            .mapIndexed { index, item ->
                if (item.groupType == ItemUiStateProvider.defaultItemUiState.groupType) {
                    item.copy(groupType = ItemGroupType.entries[index % 5])
                } else {
                    item
                }
            }.toPersistentList()
    }
}
