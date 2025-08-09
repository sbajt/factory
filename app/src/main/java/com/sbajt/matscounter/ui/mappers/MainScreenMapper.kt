package com.sbajt.matscounter.ui.mappers

import com.sbajt.matscounter.domain.models.ItemDomain
import com.sbajt.matscounter.ui.models.BuildMaterialListWrapper
import com.sbajt.matscounter.ui.models.BuildingMaterialUiState
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
                        subtitleText = selectedItem.buildMaterialListWrapper.buildingMaterialsList
                            .mapNotNull { material ->
                                itemUiStateList.firstOrNull { itemUiState -> itemUiState.name == material.name }?.groupType
                            }
                            .maxOfOrNull { it.ordinal }
                            ?.let { ItemGroupType.entries[it].getName() }
                            ?: "",
                    )
                )
            },
            itemBuildPathUiState = inputData.selectedItem?.let {
                ItemBuildPathUiState(
                    selectedItem = it,
                    selectedItemAmount = inputData.selectedItemAmount,
                    buildPathList = createBuildPathList(
                        selectedItem = it,
                        selectedItemAmount = inputData.selectedItemAmount,
                        itemUiStateList = inputData.itemUiStateList
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
        val lowerItemGroupTypeList = selectedItem.groupType.toLowerGroupsList()
        return lowerItemGroupTypeList.map { groupType ->
            val newBuildMaterialList = mutableListOf<BuildingMaterialUiState>()
            processBuildingMaterialList(
                groupType = groupType,
                multiplier = selectedItemAmount,
                newBuildMaterialList = newBuildMaterialList,
                allowedItemGroupTypeList = lowerItemGroupTypeList,
                buildMaterialList = selectedItem.buildMaterialListWrapper?.buildingMaterialsList ?: emptyList(),
                itemUiStateList = itemUiStateList
            )

            BuildMaterialListWrapper(
                titleText = groupType.getName(),
                groupType = groupType,
                buildingMaterialsList = newBuildMaterialList.toPersistentList(),
            )
        }
    }

    private fun processBuildingMaterialList(
        groupType: ItemGroupType,
        multiplier: Int,
        newBuildMaterialList: MutableList<BuildingMaterialUiState>,
        allowedItemGroupTypeList: List<ItemGroupType>,
        buildMaterialList: List<BuildingMaterialUiState>,
        itemUiStateList: List<ItemUiState>,
    ) {
        buildMaterialList.map { material ->
            if (material.isValid(allowedItemGroupTypeList, itemUiStateList)) {
                newBuildMaterialList.addToBuildingMaterialList(
                    material = material,
                    multiplier = multiplier,
                )
            } else {
                val lowerGroupType = groupType.toLowerGroupType()
                val itemUiState = itemUiStateList.firstOrNull { it.name == material.name }
                processBuildingMaterialList(
                    groupType = lowerGroupType,
                    multiplier = multiplier * material.amount,
                    newBuildMaterialList = newBuildMaterialList,
                    allowedItemGroupTypeList = allowedItemGroupTypeList - groupType,
                    buildMaterialList = itemUiState?.buildMaterialListWrapper?.buildingMaterialsList ?: emptyList(),
                    itemUiStateList = itemUiStateList,
                )
            }
        }
    }

    private fun BuildingMaterialUiState.isValid(
        allowedItemGroupTypeList: List<ItemGroupType>,
        itemUiStateList: List<ItemUiState>,
    ): Boolean {
        val buildingMaterialItemUiState = itemUiStateList.firstOrNull { it.name == name }
        return allowedItemGroupTypeList.contains(buildingMaterialItemUiState?.groupType)
    }

    private fun MutableList<BuildingMaterialUiState>.addToBuildingMaterialList(
        material: BuildingMaterialUiState,
        multiplier: Int,
    ) {
        val existingBuildingMaterial = this.firstOrNull { it.name == material.name }
        if (existingBuildingMaterial != null) {
            existingBuildingMaterial
            remove(existingBuildingMaterial)
            add(
                existingBuildingMaterial.copy(
                    amount = existingBuildingMaterial.amount + (material.amount * multiplier)
                )
            )
        } else {
            add(material)
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
            buildingMaterialsList = buildMaterials.map {
                BuildingMaterialUiState(
                    name = it.name,
                    amount = it.count,
                )
            }.toPersistentList(),
        )
    )
} else {
    null
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

fun getItmGroupTypeList(): List<ItemGroupType> = listOf(
    ItemGroupType.BASIC_MATERIAL,
    ItemGroupType.TIER1,
    ItemGroupType.TIER2,
    ItemGroupType.TIER3,
    ItemGroupType.TIER4
)

