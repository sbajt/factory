package com.sbajt.matscounter.ui.composables

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.sbajt.matscounter.ui.models.DescriptionSectionUiState

class DescriptionSectionUiStateProvider : PreviewParameterProvider<DescriptionSectionUiState> {

    override val values: Sequence<DescriptionSectionUiState> = sequenceOf(
        emptyDescriptionSectionUiState,
        defaultDescriptionSectionUiState,
    )

    companion object {
        val emptyDescriptionSectionUiState = DescriptionSectionUiState()

        val defaultDescriptionSectionUiState = DescriptionSectionUiState(
            selectedItem = ItemUiStateProvider.defaultItemUiState,
        )
    }
}
