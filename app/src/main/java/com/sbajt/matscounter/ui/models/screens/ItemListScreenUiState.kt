package com.sbajt.matscounter.ui.models.screens

import com.sbajt.matscounter.ui.models.views.ItemUiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

sealed class ItemListScreenUiState {

    object Loading : ItemListScreenUiState()

    object Empty : ItemListScreenUiState()

    data class Content(
        val itemUiStateList: ImmutableList<ItemUiState> = persistentListOf(),
    ) : ItemListScreenUiState()
}
