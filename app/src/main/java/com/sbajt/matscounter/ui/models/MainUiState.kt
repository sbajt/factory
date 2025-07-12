package com.sbajt.matscounter.ui.models

internal data class MainUiState(
    val descriptionUiState: DescriptionSectionUiState = DescriptionSectionUiState(),
    val inputSectionUiState: InputSectionUiState = InputSectionUiState(),
    val itemUiStateList: List<ItemUiState> = emptyList(),
)

data class DescriptionSectionUiState(
    val selectedItem: ItemUiState? = null
)

data class InputSectionUiState(
    val selectedItem: ItemUiState? = null,
    val itemCount: Int = 0,
)

