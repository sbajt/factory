package com.sbajt.matscounter.ui.composables

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.sbajt.matscounter.ui.models.DescriptionSectionUiState
import com.sbajt.matscounter.ui.models.InputSectionUiState
import com.sbajt.matscounter.ui.models.MainScreenUiState
import kotlinx.collections.immutable.persistentListOf

class MainScreenPreviewProvider : PreviewParameterProvider<MainScreenUiState> {

    override val values: Sequence<MainScreenUiState> = sequenceOf(
        loadingItem,
        emptyItem,
        emptyContent,
        commonItem,
        commonWithSelectedItem,
    )

    companion object {

        val loadingItem = MainScreenUiState.Loading

        val emptyItem = MainScreenUiState.Empty

        val emptyContent = MainScreenUiState.Content(
            descriptionUiState = DescriptionSectionUiState(),
            inputSectionUiState = InputSectionUiState(),
            itemUiStateList = persistentListOf()
        )

        val commonItem = MainScreenUiState.Content(
            descriptionUiState = DescriptionSectionUiState(),
            inputSectionUiState = InputSectionUiState(),
            itemUiStateList = ItemUiStateProvider.mockItemUiStateList(),
        )

        val commonWithSelectedItem = MainScreenUiState.Content(
            descriptionUiState = DescriptionSectionUiState(
                selectedItem = ItemUiStateProvider.defaultItemUiState
            ),
            inputSectionUiState = InputSectionUiState(
                selectedItem = ItemUiStateProvider.defaultItemUiState,
                itemCount = 1
            ),
            itemUiStateList = ItemUiStateProvider.mockItemUiStateList(),
        )
    }
}
