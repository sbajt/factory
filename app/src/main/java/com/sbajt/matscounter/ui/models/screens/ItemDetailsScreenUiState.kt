package com.sbajt.matscounter.ui.models.screens

import com.sbajt.matscounter.ui.models.appBars.AppBarState
import com.sbajt.matscounter.ui.models.views.BuildMaterialListWrapper
import com.sbajt.matscounter.ui.models.views.ItemUiState

data class ItemDetailsScreenUiState(
    val appBarState: AppBarState.ItemDetailsAppBar? = null,
    val selectedItem: ItemUiState?,
    val selectedItemAmount: Int = 0,
    val selectedItemBuildMaterialListWrapper: BuildMaterialListWrapper? = null,
) : BaseScreeUiState
