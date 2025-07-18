package com.sbajt.matscounter.ui.composables

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.sbajt.matscounter.ui.models.DescriptionSectionUiState
import com.sbajt.matscounter.ui.models.InputSectionUiState
import com.sbajt.matscounter.ui.models.ItemGroupType
import com.sbajt.matscounter.ui.models.ItemUiState
import com.sbajt.matscounter.ui.models.MainScreenUiState
import kotlinx.collections.immutable.ImmutableList
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
            itemUiStateList = createItemUiStateList(),
        )

        val commonWithSelectedItem = MainScreenUiState.Content(
            descriptionUiState = DescriptionSectionUiState(
                selectedItem = createItemUiState()
            ),
            inputSectionUiState = InputSectionUiState(
                selectedItem = createItemUiState(),
                itemCount = 1
            ),
            itemUiStateList = createItemUiStateList(),
        )

        fun createItemUiState() = ItemUiState(
            name = "Cardboard",
            imageName = "ic_ic_cardboard",
            groupType = ItemGroupType.BASIC_MATERIAL
        )

        fun createItemUiStateList() = List(10) { index ->
            ItemUiState(
                name = "Item $index",
                imageName = "ic_cardboard",
                groupType = ItemGroupType.BASIC_MATERIAL
            )
        } as ImmutableList<ItemUiState>
    }
}
