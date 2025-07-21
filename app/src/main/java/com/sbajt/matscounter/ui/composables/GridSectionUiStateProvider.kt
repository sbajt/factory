package com.sbajt.matscounter.ui.composables

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.sbajt.matscounter.ui.models.ItemUiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

class GridSectionUiStateProvider: PreviewParameterProvider<ImmutableList<ItemUiState>> {

    override val values: Sequence<ImmutableList<ItemUiState>> = sequenceOf(
        emptyGridSectionUiState,
        defaultGridSectionUiState,
    )

    companion object {
        val emptyGridSectionUiState: ImmutableList<ItemUiState> = persistentListOf()

        val defaultGridSectionUiState: ImmutableList<ItemUiState> = ItemUiStateProvider.mockItemUiStateList()
    }
}
