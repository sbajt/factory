package com.sbajt.matscounter.ui.models

import com.sbajt.matscounter.ui.models.views.ItemUiState

data class AppState(
    val selectedItem: ItemUiState? = null,
    val selectedItemAmount: Int = 0,
)
