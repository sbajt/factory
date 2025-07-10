package com.sbajt.matscounter.ui.models

data class MainUiState(
    val inputScreenUiState: InputScreenUiState? = null,
    val itemGridUiState: List<ItemUiState> = emptyList(),
)

data class InputScreenUiState(
    val selectedItem: ItemUiState? = null,
    val itemCount: Int = 0,
)

