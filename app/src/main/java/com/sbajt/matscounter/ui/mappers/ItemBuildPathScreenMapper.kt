package com.sbajt.matscounter.ui.mappers

import com.sbajt.matscounter.ui.models.ItemGroupType
import com.sbajt.matscounter.ui.models.screens.ItemBuildPathScreenUiState
import com.sbajt.matscounter.ui.models.views.BuildMaterialListWrapper
import com.sbajt.matscounter.ui.models.views.BuildMaterialUiState
import com.sbajt.matscounter.ui.models.views.ItemUiState
import kotlinx.collections.immutable.toPersistentList

class ItemBuildPathScreenMapper {

    fun mapToUiState(inputData: InputData): ItemBuildPathScreenUiState = with(inputData) {
        ItemBuildPathScreenUiState.Content(
            selectedItem = selectedItem,
            selectedItemAmount = selectedItemAmount,
            selectedItemBuildMaterialListWrapperList = createBuildPathList(
                selectedItem = selectedItem,
                selectedItemAmount = selectedItemAmount,
                itemUiStateList = itemUiStateList,
            )
        )
    }

    private fun createBuildPathList(
        selectedItem: ItemUiState,
        selectedItemAmount: Int,
        itemUiStateList: List<ItemUiState>,
    ): List<BuildMaterialListWrapper> {
        val lowerGroupTypesForSelectedItem = selectedItem.groupType.toLowerGroupsList()

        return lowerGroupTypesForSelectedItem.map { groupType ->
            val groupBuildMaterialList = collectMaterialsForSpecificGroup(
                itemToBuild = selectedItem,
                amountToBuild = selectedItemAmount,
                targetGroupType = groupType,
                itemUiStateList = itemUiStateList
            )
            BuildMaterialListWrapper(
                titleText = groupType.getName(),
                groupType = groupType,
                buildMaterialsList = groupBuildMaterialList.toPersistentList(),
            )
        }
    }

    private fun collectMaterialsForSpecificGroup(
        itemToBuild: ItemUiState,
        amountToBuild: Int,
        targetGroupType: ItemGroupType,
        itemUiStateList: List<ItemUiState>
    ): List<BuildMaterialUiState> {
        val collectedMaterials = mutableListOf<BuildMaterialUiState>()

        fun findRecursive(currentItem: ItemUiState, currentAmountNeeded: Int) {
            currentItem.buildMaterialListWrapper?.buildMaterialsList?.forEach { materialRequirement ->
                val subItemName = materialRequirement.name
                val subItemAmountPerParent = materialRequirement.amount
                val totalSubItemAmountNeeded = subItemAmountPerParent * currentAmountNeeded

                val subItem = itemUiStateList.find { it.name == subItemName }

                if (subItem != null) {
                    if (subItem.groupType == targetGroupType || subItem.groupType.toLowerGroupsList().contains(subItem.groupType)) {
                        // This sub-item is of the target group type. Add it to the collection.
                        // We use the materialRequirement's amount (amount per parent) and multiply by how many parents are needed.
                        collectedMaterials.addToBuildMaterialList(
                            material = BuildMaterialUiState(name = subItem.name, amount = subItemAmountPerParent),
                            multiplier = currentAmountNeeded
                        )
                    } else if (subItem.groupType.toLowerGroupsList().contains(targetGroupType)) {
                        // This sub-item is of a higher tier that could contain the targetGroupType materials.
                        // Recurse to find materials for this sub-item.
                        findRecursive(subItem, totalSubItemAmountNeeded)
                    }
                    // If subItem.groupType is lower than targetGroupType or cannot produce it, we stop down this path.
                }
            }
        }

        // Start the recursion with the main item to build.
        findRecursive(itemToBuild, amountToBuild)
        return collectedMaterials
    }

    fun MutableList<BuildMaterialUiState>.addToBuildMaterialList(
        material: BuildMaterialUiState,
        multiplier: Int,
    ) {
        val existingMaterial = this.find { it.name == material.name }
        val newAmount = (existingMaterial?.amount ?: 0) + (material.amount * multiplier)
        existingMaterial?.let { this[indexOf(it)] = it.copy(amount = newAmount) } ?: run {
            add(material.copy(amount = material.amount * multiplier))
        }
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



