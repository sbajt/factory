package com.sbajt.matscounter.ui.composables

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.sbajt.matscounter.ui.models.InputSectionUiState

class InputSectionUiStateProvider : PreviewParameterProvider<InputSectionUiState> {

    override val values: Sequence<InputSectionUiState> = sequenceOf(
        emptyInputSectionUiState,
        defaultInputSectionUiState,
        multipleItemCountInputSectionUiState,
    )

    companion object {
        val emptyInputSectionUiState = InputSectionUiState()

        val defaultInputSectionUiState = InputSectionUiState(
            selectedItem = ItemUiStateProvider.defaultItemUiState,
            itemCount = 1,
        )

        val multipleItemCountInputSectionUiState = InputSectionUiState(
            selectedItem = ItemUiStateProvider.defaultItemUiState,
            itemCount = 5,
        )

    }
}
