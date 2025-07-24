package com.sbajt.matscounter.ui.models

import kotlinx.collections.immutable.ImmutableList

sealed class MainScreenUiState {

    data object Loading : MainScreenUiState()

    data object Empty : MainScreenUiState()

    data class Content(
        val descriptionUiState: DescriptionSectionUiState?,
        val inputSectionUiState: InputSectionUiState?,
        val itemUiStateList: ImmutableList<ItemUiState>,
    ) : MainScreenUiState()
}
