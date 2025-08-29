package com.sbajt.matscounter.ui.composables.previewProviders

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.sbajt.matscounter.ui.composables.ItemUiStateProvider
import com.sbajt.matscounter.ui.models.screens.ItemListScreenUiState

class MainScreenPreviewProvider : PreviewParameterProvider<ItemListScreenUiState> {

    override val values: Sequence<ItemListScreenUiState> = sequenceOf(
        loading,
        empty,
        itemUiStateList,
    )

    companion object {

        val loading = ItemListScreenUiState.Loading

        val empty = ItemListScreenUiState.Empty

        val itemUiStateList = ItemListScreenUiState.Content(
            itemUiStateList = ItemUiStateProvider.Companion.mockItemUiStateList()
        )
    }
}
