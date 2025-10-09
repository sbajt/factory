package com.sbajt.matscounter.ui.models.screens

import com.sbajt.matscounter.ui.models.appBars.AppBarState
import com.sbajt.matscounter.ui.models.views.BuildMaterialListWrapper
import com.sbajt.matscounter.ui.models.views.ItemUiState

data class ItemBuildPathScreenUiState(
    val appBarState: AppBarState.ItemBuildPathAppBar? = null,
    val selectedItem: ItemUiState,
    val selectedItemAmount: Int,
    val selectedItemBuildMaterialListWrapperList: List<BuildMaterialListWrapper>,
) : BaseScreeUiState
