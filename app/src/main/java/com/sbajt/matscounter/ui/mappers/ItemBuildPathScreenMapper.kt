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
                itemList = itemUiStateList,
            )
        )
    }

    private fun createBuildPathList(
        selectedItem: ItemUiState,
        selectedItemAmount: Int,
        itemList: List<ItemUiState>,
    ): List<BuildMaterialListWrapper> {
        val buildMaterialWrapperList = createInitialBuildMaterialWrapperList(
            selectedItem = selectedItem,
            selectedItemAmount = selectedItemAmount,
            itemList = itemList,
        )
        selectedItem.groupType.toLowerGroupsList().forEach { groupType ->
            updateBuildMaterialWrapperList(
                buildMaterialWrapperList = buildMaterialWrapperList,
                groupType = groupType,
                multiplier = selectedItemAmount,
                itemList = itemList
            )
        }

        return buildMaterialWrapperList.toList()
    }

    private fun createInitialBuildMaterialWrapperList(
        selectedItem: ItemUiState,
        selectedItemAmount: Int,
        itemList: List<ItemUiState>,
    ): MutableList<BuildMaterialListWrapper> = if (
        selectedItem.buildMaterialListWrapper != null
        && selectedItem.buildMaterialListWrapper.buildMaterialsList.any { buildMaterial ->
            itemList.find { it.name == buildMaterial.name }?.groupType == selectedItem.groupType
        }
    ) {
        mutableListOf(
            selectedItem.buildMaterialListWrapper.copy(
                titleText = "Item build materials",
                groupType = selectedItem.groupType,
                buildMaterialsList = selectedItem.buildMaterialListWrapper.buildMaterialsList.map {
                    it.copy(amount = it.amount * selectedItemAmount)
                }
            )
        )
    } else {
        mutableListOf()
    }

    private fun updateBuildMaterialWrapperList(
        buildMaterialWrapperList: MutableList<BuildMaterialListWrapper>,
        groupType: ItemGroupType,
        multiplier: Int,
        itemList: List<ItemUiState>,
    ) {
        val previousBuildMaterialList = if (buildMaterialWrapperList.isNotEmpty()) {
            buildMaterialWrapperList.last().buildMaterialsList
        } else {
            emptyList()
        }
        val buildMaterialsGroup = mutableListOf<BuildMaterialUiState>()

        previousBuildMaterialList.forEach { buildMaterial ->
            val buildMaterialItem = itemList.find { it.name == buildMaterial.name }
            if (buildMaterialItem?.groupType == groupType) {
                buildMaterial.copy(amount = buildMaterial.amount * multiplier)
            } else {
                buildMaterialsGroup.remove(buildMaterial)
                buildMaterialsGroup.addAll(
                    splitBuildMaterial(
                        buildMaterial = buildMaterial,
                        itemList = itemList,
                        multiplier = multiplier,
                    )
                )
            }
        }

        buildMaterialWrapperList.add(
            BuildMaterialListWrapper(
                titleText = groupType.getName(),
                groupType = groupType,
                buildMaterialsList = buildMaterialsGroup
            )
        )
    }

    private fun splitBuildMaterial(
        buildMaterial: BuildMaterialUiState,
        itemList: List<ItemUiState>,
        multiplier: Int,
    ): List<BuildMaterialUiState> {
        val item = itemList.find { it.name == buildMaterial.name }
        return item?.buildMaterialListWrapper?.buildMaterialsList?.map { buildMaterial ->
            buildMaterial.copy(amount = buildMaterial.amount * multiplier)
        }
            ?: emptyList()
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
