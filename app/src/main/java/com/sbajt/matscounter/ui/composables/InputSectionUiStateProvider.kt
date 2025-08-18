package com.sbajt.matscounter.ui.composables

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.sbajt.matscounter.ui.models.InputSectionUiState

class InputSectionUiStateProvider : PreviewParameterProvider<InputSectionUiState> {

    override val values: Sequence<InputSectionUiState> = sequenceOf(
        defaultInputSectionUiState,
        multipleItemCountInputSectionUiState,
    )

    companion object {
        val defaultInputSectionUiState = InputSectionUiState(
            selectedItem = ItemUiStateProvider.tier1ItemUiState,
            itemCount = 1,
        )

        val multipleItemCountInputSectionUiState = InputSectionUiState(
            selectedItem = ItemUiStateProvider.tier1ItemUiState,
            itemCount = 5,
        )
    }
}
