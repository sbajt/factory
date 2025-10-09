package com.sbajt.matscounter.ui.models.screens

import com.sbajt.matscounter.ui.models.appBars.AppBarState
import com.sbajt.matscounter.ui.models.views.ItemUiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

class ItemListScreenUiState(
    val appBarState: AppBarState.ItemListAppBar? = null,
    val itemUiStateList: ImmutableList<ItemUiState> = persistentListOf(),
): BaseScreeUiState


