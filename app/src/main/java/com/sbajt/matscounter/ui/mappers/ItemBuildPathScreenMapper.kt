package com.sbajt.matscounter.ui.mappers

import com.sbajt.matscounter.ui.models.ItemGroupType
import com.sbajt.matscounter.ui.models.screens.ItemBuildPathScreenUiState
import com.sbajt.matscounter.ui.models.views.BuildMaterialListWrapper
import com.sbajt.matscounter.ui.models.views.BuildMaterialUiState
import com.sbajt.matscounter.ui.models.views.ItemUiState

class ItemBuildPathScreenMapper {

    fun mapToUiState(inputData: InputData): ItemBuildPathScreenUiState = with(inputData) {
        ItemBuildPathScreenUiState.Content(
            selectedItem = selectedItem,
            selectedItemAmount = selectedItemAmount,
            selectedItemBuildMaterialListWrapperList = createBuildPathList(
                selectedItem = selectedItem,
                selectedItemAmount = selectedItemAmount,
                allItemList = itemUiStateList,
            )
        )
    }

    private fun createBuildPathList(
        selectedItem: ItemUiState,
        selectedItemAmount: Int,
        allItemList: List<ItemUiState>,
    ): List<BuildMaterialListWrapper> {
        val groupTypesForSelectedItem = selectedItem.groupType.toLowerGroupsList()
        val finalBuildMaterialListWrapperList = listOfNotNull(selectedItem.buildMaterialListWrapper?.copy(titleText = "Primary build materials")).toMutableList()
        groupTypesForSelectedItem.forEach { groupType ->
            finalBuildMaterialListWrapperList.add(
                BuildMaterialListWrapper(
                    titleText = groupType.getName(),
                    buildMaterialsList = modifyBuildMaterialList(
                        groupType = groupType,
                        amount = selectedItemAmount,
                        allItemList = allItemList,
                    )
                )
            )
        }
        return finalBuildMaterialListWrapperList
    }

    private fun modifyBuildMaterialList(
        groupType: ItemGroupType,
        amount: Int,
        allItemList: List<ItemUiState>,
    ): List<BuildMaterialUiState> = allItemList.filter { it.isValid(groupType) }.map {
        BuildMaterialUiState(
            name = it.name,
            amount = amount * amount
        )
    }

    private fun ItemUiState.isValid(groupType: ItemGroupType): Boolean = groupType == this.groupType || this.groupType.toLowerGroupsList().contains(groupType)

    companion object {

        class InputData(
            val selectedItem: ItemUiState,
            val selectedItemAmount: Int,
            val itemUiStateList: List<ItemUiState>,
        )
    }
}

fun ItemGroupType.toLowerGroupType(): ItemGroupType = when (this) {
    ItemGroupType.TIER1 -> ItemGroupType.BASIC_MATERIAL
    ItemGroupType.TIER2 -> ItemGroupType.TIER1
    ItemGroupType.TIER3 -> ItemGroupType.TIER2
    ItemGroupType.TIER4 -> ItemGroupType.TIER3
    else -> this
}

fun ItemGroupType.toLowerGroupsList(): List<ItemGroupType> = when (this) {
    ItemGroupType.TIER1 -> listOf(ItemGroupType.BASIC_MATERIAL)
    ItemGroupType.TIER2 -> listOf(ItemGroupType.TIER1, ItemGroupType.BASIC_MATERIAL)
    ItemGroupType.TIER3 -> listOf(ItemGroupType.TIER2, ItemGroupType.TIER1, ItemGroupType.BASIC_MATERIAL)
    ItemGroupType.TIER4 -> listOf(ItemGroupType.TIER3, ItemGroupType.TIER2, ItemGroupType.TIER1, ItemGroupType.BASIC_MATERIAL)
    else -> emptyList()
}

fun getGroupTypeList(): List<ItemGroupType> = listOf(
    ItemGroupType.BASIC_MATERIAL,
    ItemGroupType.TIER1,
    ItemGroupType.TIER2,
    ItemGroupType.TIER3,
    ItemGroupType.TIER4
)



