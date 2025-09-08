package com.sbajt.matscounter.ui.mappers

import com.sbajt.matscounter.ui.models.ItemGroupType
import com.sbajt.matscounter.ui.models.screens.ItemDetailsScreenUiState
import com.sbajt.matscounter.ui.models.views.ItemUiState

class ItemDetailsScreenMapper {

    fun mapToUiState(inputData: InputData): ItemDetailsScreenUiState = with(inputData) {
        ItemDetailsScreenUiState.Content(
            selectedItem = selectedItem,
            selectedItemAmount = selectedItemAmount,
            selectedItemBuildMaterialListWrapper = selectedItem?.buildMaterialListWrapper?.copy(
                titleText = "Build materials",
                buildMaterialsList = selectedItem.buildMaterialListWrapper.buildMaterialsList.map {
                    it.copy(amount = it.amount * selectedItemAmount)
                }
            )
        )
    }

    companion object {
        class InputData(
            val selectedItem: ItemUiState?,
            val selectedItemAmount: Int,
        )
    }
}

fun Int?.toGroupType(): ItemGroupType = ItemGroupType.entries
    .firstOrNull { it.ordinal == this } ?: ItemGroupType.NONE





