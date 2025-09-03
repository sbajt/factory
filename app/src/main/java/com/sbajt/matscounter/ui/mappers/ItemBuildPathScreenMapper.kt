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
        val lowerGroupTypeList = selectedItem.groupType.toLowerGroupsList()
        val mutableBuildMaterialsList = mutableListOf<BuildMaterialUiState>()
        mutableBuildMaterialsList.addAll(selectedItem.buildMaterialListWrapper?.buildMaterialsList?.map { it.copy(amount = it.amount * selectedItemAmount) } ?: emptyList())
        val finalBuildMaterialWrapperList = listOfNotNull(selectedItem.buildMaterialListWrapper?.copy(titleText = "Primary build materials")).toMutableList()
        lowerGroupTypeList.forEach { groupType ->
            val buildingMaterialGroupList = mutableListOf<BuildMaterialUiState>()


            mutableBuildMaterialsList.forEach { buildMaterial ->
                val item = allItemList.find { item -> item.name == buildMaterial.name }
                when (item?.groupType?.compareTo(groupType)) {
                    -1 -> {
                        mutableBuildMaterialsList.remove(buildMaterial)
                        item.buildMaterialListWrapper?.buildMaterialsList?.forEach {
                            mutableBuildMaterialsList.add(it.copy(amount = it.amount * buildMaterial.amount))
                        }
                    }
                    0, 1 -> {
                        val existingBuildMaterial = mutableBuildMaterialsList.find { it.name == item.name }
                        if(existingBuildMaterial == null) {
                            buildingMaterialGroupList.add(buildMaterial)
                            mutableBuildMaterialsList.remove(buildMaterial)
                            val copy = buildMaterial.copy(amount = buildMaterial.amount * selectedItemAmount)
                            mutableBuildMaterialsList.add(copy)
                        } else {

                            existingBuildMaterial.copy(amount = existingBuildMaterial.amount * buildMaterial.amount)
                        }
                    }
                }
            }

            finalBuildMaterialWrapperList.add(
                BuildMaterialListWrapper(
                    titleText = groupType.getName(),
                    buildMaterialsList = buildingMaterialGroupList,
                )
            )

        }
        return finalBuildMaterialWrapperList
    }

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
