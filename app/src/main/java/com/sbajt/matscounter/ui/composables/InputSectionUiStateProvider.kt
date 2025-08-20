package com.sbajt.matscounter.ui.composables

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.sbajt.matscounter.ui.models.InputSectionUiState

class InputSectionUiStateProvider : PreviewParameterProvider<InputSectionUiState> {

    override val values: Sequence<InputSectionUiState> = sequenceOf(
        defaultInputSectionUiState,
        inputSectionUiState5ItemCount,
        inputSectionUiState10ItemCount,
    )

    companion object {
        val defaultInputSectionUiState = InputSectionUiState(
            selectedItem = ItemUiStateProvider.tier1ItemUiState,
            itemCount = 1,
        )

        val inputSectionUiState5ItemCount = InputSectionUiState(
            selectedItem = ItemUiStateProvider.tier1ItemUiState,
            itemCount = 5,
        )

        val inputSectionUiState10ItemCount = InputSectionUiState(
            selectedItem = ItemUiStateProvider.tier1ItemUiState,
            itemCount = 10,
        )
    }
}
