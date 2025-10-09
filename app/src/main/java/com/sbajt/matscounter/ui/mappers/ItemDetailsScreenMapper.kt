package com.sbajt.matscounter.ui.mappers

import com.sbajt.matscounter.ui.models.appBars.AppBarActionType
import com.sbajt.matscounter.ui.models.appBars.AppBarState
import com.sbajt.matscounter.ui.models.screens.ItemDetailsScreenUiState
import com.sbajt.matscounter.ui.models.views.ItemUiState
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ItemDetailsScreenMapper : KoinComponent {

    private val matsWrapperMapper: BuildMaterialListWrapperMapper by inject()

    fun mapToUiState(inputData: InputData): ItemDetailsScreenUiState = with(inputData) {
        ItemDetailsScreenUiState(
            selectedItem = selectedItem,
            selectedItemAmount = selectedItemAmount,
            selectedItemBuildMaterialListWrapper = matsWrapperMapper.mapToUiState(
                inputData = BuildMaterialListWrapperMapper.Companion.InputData(
                    titleText = "Build materials",
                    groupType = selectedItem?.groupType,
                    initialItemAmount = selectedItemAmount,
                    initialBuildMaterialSet = selectedItem?.buildMaterialListWrapper?.buildMaterialsList?.toHashSet()
                        ?: hashSetOf(),
                    itemList = itemList,
                )
            ),
            appBarState = AppBarState.ItemDetailsAppBar(
                title = selectedItem?.name ?: "",
                actionList = listOf(AppBarActionType.ACTION_BUILD_PATH)
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





