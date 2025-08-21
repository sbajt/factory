package com.sbajt.matscounter.ui.mappers

import com.sbajt.matscounter.domain.models.ItemDomain
import com.sbajt.matscounter.ui.models.BuildMaterialListWrapper
import com.sbajt.matscounter.ui.models.BuildMaterialUiState
import com.sbajt.matscounter.ui.models.ItemBuildPathUiState
import com.sbajt.matscounter.ui.models.ItemDetailsScreenUiState
import com.sbajt.matscounter.ui.models.ItemGroupType
import com.sbajt.matscounter.ui.models.ItemUiState
import com.sbajt.matscounter.ui.models.MainScreenUiState
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toPersistentList

class MainScreenMapper {

    fun mapToUiState(inputData: InputData): MainScreenUiState = with(inputData) {
        MainScreenUiState.Content(
            itemUiStateList = itemUiStateList.toImmutableList(),
            itemDetailsUiState = selectedItem?.let { selectedItem ->
                ItemDetailsScreenUiState(
                    selectedItem = selectedItem,
                    selectedItemAmount = selectedItemAmount,
                    selectedItemBuildMaterialListWrapper = selectedItem.buildMaterialListWrapper?.copy(
                        titleText = "Build materials",
                    )
                )
            },
            itemBuildPathUiState = selectedItem?.let {
                ItemBuildPathUiState(
                    selectedItem = it,
                    selectedItemAmount = selectedItemAmount,
                    buildPathList = createBuildPathList(
                        selectedItem = it,
                        selectedItemAmount = selectedItemAmount,
                        itemUiStateList = itemUiStateList
                    )
                )
            }
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
                    if (subItem.groupType == targetGroupType) {
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

    private fun ItemGroupType?.isValid(validGroupTypeList: List<ItemGroupType>) = validGroupTypeList.contains(this)

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
            val selectedItem: ItemUiState?,
            val selectedItemAmount: Int,
            val itemUiStateList: List<ItemUiState>
        )
    }
}

fun ItemDomain?.toItemUiState(): ItemUiState? = if (this != null) {
    val itemGroupType = groupType.toGroupType()
    val lowerGroupType = itemGroupType.toLowerGroupType()
    ItemUiState(
        name = name ?: "",
        imageName = imageName ?: "",
        groupType = itemGroupType,
        buildMaterialListWrapper = BuildMaterialListWrapper(
            titleText = lowerGroupType.getName(),
            groupType = lowerGroupType,
            buildMaterialsList = buildMaterials.map {
                BuildMaterialUiState(
                    name = it.name,
                    amount = it.count,
                )
            }.toPersistentList(),
        )
    )
} else {
    null
}

private fun ItemGroupType.toLowerGroupType(): ItemGroupType = when (this) {
    ItemGroupType.TIER1 -> ItemGroupType.BASIC_MATERIAL
    ItemGroupType.TIER2 -> ItemGroupType.TIER1
    ItemGroupType.TIER3 -> ItemGroupType.TIER2
    ItemGroupType.TIER4 -> ItemGroupType.TIER3
    else -> this
}

fun Int?.toGroupType(): ItemGroupType = ItemGroupType.entries
    .firstOrNull { it.ordinal == this } ?: ItemGroupType.NONE

fun ItemGroupType?.getName() = this
    ?.takeIf { it != ItemGroupType.NONE }
    ?.name
    ?.lowercase()
    ?.replaceFirstChar { it.uppercase() }
    ?.replace("_", " ")
    ?: ""

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

