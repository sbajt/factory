package com.sbajt.matscounter.ui.models.screens

import com.sbajt.matscounter.ui.models.views.BuildMaterialListWrapper
import com.sbajt.matscounter.ui.models.views.ItemUiState

sealed class ItemDetailsScreenUiState {

    object Loading : ItemDetailsScreenUiState()

    object Empty : ItemDetailsScreenUiState()

    data class Content(
        val selectedItem: ItemUiState?,
        val selectedItemAmount: Int = 0,
        val selectedItemBuildMaterialListWrapper: BuildMaterialListWrapper? = null,
    ) : ItemDetailsScreenUiState()
}
