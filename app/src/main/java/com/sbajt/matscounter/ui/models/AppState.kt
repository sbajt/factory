package com.sbajt.matscounter.ui.models

import com.sbajt.matscounter.ui.models.appBars.AppBarState
import com.sbajt.matscounter.ui.models.views.ItemUiState

data class AppState(
    val appBarState: AppBarState? = null,
    val selectedItem: ItemUiState? = null,
    val selectedItemAmount: Int = 0,
)
