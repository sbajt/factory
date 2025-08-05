package com.sbajt.matscounter.ui.composables

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.sbajt.matscounter.ui.models.MainScreenUiState

class MainScreenPreviewProvider : PreviewParameterProvider<MainScreenUiState> {

    override val values: Sequence<MainScreenUiState> = sequenceOf(
        loading,
        empty,
        itemUiStateList,
        itemDetails,
        itemBuildPath
    )

    companion object {

        val loading = MainScreenUiState.Loading

        val empty = MainScreenUiState.Empty

        val itemUiStateList = MainScreenUiState.Content(
            itemUiStateList = ItemUiStateProvider.mockItemUiStateList()
        )
        val itemDetails = MainScreenUiState.Content(
            itemDetailsUiState = ItemDetailsUiStateProvider.defaultItemDetailsScreenUiState
        )
        val itemBuildPath = MainScreenUiState.Content(
            itemBuildPathUiState = ItemBuildPathUiStateProvider.defaultBuildPathUiState
        )
    }
}
