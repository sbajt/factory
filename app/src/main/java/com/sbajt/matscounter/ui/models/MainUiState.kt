package com.sbajt.matscounter.ui.models

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class MainUiState(
    val descriptionUiState: DescriptionSectionUiState = DescriptionSectionUiState(),
    val inputSectionUiState: InputSectionUiState = InputSectionUiState(),
    val itemUiStateList: ImmutableList<ItemUiState> = persistentListOf(),
)

data class DescriptionSectionUiState(
    val selectedItem: ItemUiState? = null
)

data class InputSectionUiState(
    val selectedItem: ItemUiState? = null,
    val itemCount: Int = 0,
)

