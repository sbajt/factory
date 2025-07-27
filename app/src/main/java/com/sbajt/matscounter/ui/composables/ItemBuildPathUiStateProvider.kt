package com.sbajt.matscounter.ui.composables

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.sbajt.matscounter.ui.models.ItemBuildPathUiState

class ItemBuildPathUiStateProvider : PreviewParameterProvider<ItemBuildPathUiState> {

    override val values: Sequence<ItemBuildPathUiState> = sequenceOf(
        defaultBuildPathUiState
    )

    companion object {

        val defaultBuildPathUiState: ItemBuildPathUiState = ItemBuildPathUiState(
            selectedItem = ItemUiStateProvider.defaultItemUiState
        )
    }

}
