package com.sbajt.matscounter.ui.models.screens

import com.sbajt.matscounter.ui.models.views.BuildMaterialListWrapper
import com.sbajt.matscounter.ui.models.views.ItemUiState

sealed class ItemBuildPathScreenUiState {

    object Loading : ItemBuildPathScreenUiState()

    object Empty : ItemBuildPathScreenUiState()

    data class Content(
        val selectedItem: ItemUiState,
        val selectedItemAmount: Int,
        val selectedItemBuildMaterialListWrapperList: List<BuildMaterialListWrapper>,
    ) : ItemBuildPathScreenUiState()
}
