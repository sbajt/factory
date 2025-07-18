package com.sbajt.matscounter.ui.models

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

sealed class MainScreenUiState {

    data object Loading : MainScreenUiState()

    data object Empty : MainScreenUiState()

    data class Content(
        val descriptionUiState: DescriptionSectionUiState = DescriptionSectionUiState(),
        val inputSectionUiState: InputSectionUiState = InputSectionUiState(),
        val itemUiStateList: ImmutableList<ItemUiState> = persistentListOf(),
    ) : MainScreenUiState()
}
