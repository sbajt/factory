package com.sbajt.matscounter.ui.mappers

import com.sbajt.matscounter.ui.models.screens.ItemDetailsScreenUiState
import com.sbajt.matscounter.ui.models.toLowerGroupType
import com.sbajt.matscounter.ui.models.views.ItemUiState
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ItemDetailsScreenMapper : KoinComponent {

    private val matsWrapperMapper: BuildMaterialListWrapperMapper by inject()

    fun mapToUiState(inputData: InputData): ItemDetailsScreenUiState = with(inputData) {
        ItemDetailsScreenUiState.Content(
            selectedItem = selectedItem,
            selectedItemAmount = selectedItemAmount,
            selectedItemBuildMaterialListWrapper = matsWrapperMapper.mapToUiState(
                inputData = BuildMaterialListWrapperMapper.Companion.InputData(
                    titleText = "Build materials",
                    groupType = selectedItem?.groupType,
                    initialItemAmount = selectedItemAmount,
                    initialBuildMaterialsList = selectedItem?.buildMaterialListWrapper?.buildMaterialsList?.map {
                        it.copy(amount = it.amount * selectedItemAmount)
                    } ?: emptyList(),
                    itemList = inputData.itemList,
                    hasOnlyInitialBuildMaterials = true,
                )
            )
        )
    }

    companion object {

        data class InputData(
            val selectedItem: ItemUiState?,
            val selectedItemAmount: Int,
            val itemList: List<ItemUiState>,
        )
    }
}





