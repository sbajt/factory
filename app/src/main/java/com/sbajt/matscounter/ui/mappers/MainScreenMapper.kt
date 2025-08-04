package com.sbajt.matscounter.ui.mappers

import com.sbajt.matscounter.domain.models.ItemDomain
import com.sbajt.matscounter.ui.models.BuildMaterialListUiState
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
            itemUiStateList = itemUiStateList
                .sortedWith(compareBy({ it.groupType }, { it.name }))
                .toImmutableList(),
            itemDetailsUiState = inputData.selectedItem?.let { selectedItem ->
                ItemDetailsScreenUiState(
                    selectedItem = selectedItem,
                    selectedItemNumber = selectedItemNumber,
                    selectedItemBuildingMaterialListUiState = createBuildMaterialList(
                        groupType = selectedItem.groupType,
                        selectedItemNumber = selectedItemNumber,
                        buildingMaterialsList = selectedItem.buildingMaterials
                    ),
                )
            },
            itemBuildPathUiState = inputData.selectedItem?.let {
                ItemBuildPathUiState(
                    selectedItem = it,
                    selectedItemNumber = inputData.selectedItemNumber,
                    buildPathList = createBuildPathList(
                        selectedItem = inputData.selectedItem,
                        selectedItemNumber = inputData.selectedItemNumber,
                    )
                )
            }
        )
    }

    private fun createBuildPathList(
        selectedItem: ItemUiState,
        selectedItemNumber: Int,
    ): List<BuildMaterialListUiState> = selectedItem
        .groupType
        .toLowerGroupsList()
        .map { groupType ->
            createBuildMaterialList(
                groupType = groupType,
                selectedItemNumber = selectedItemNumber,
                buildingMaterialsList = selectedItem.buildingMaterials,
            )
        }

    private fun createBuildMaterialList(
        groupType: ItemGroupType,
        selectedItemNumber: Int,
        buildingMaterialsList: List<BuildingMaterialUiState>,
    ) = BuildMaterialListUiState(
        titleText = groupType.getName(),
        groupType = groupType,
        selectedItemNumber = selectedItemNumber,
        buildingMaterialsList = buildingMaterialsList.map { material ->
            BuildingMaterialUiState(
                name = material.name,
                groupType = material.groupType,
                count = material.count * selectedItemNumber
            )
        }.toPersistentList()
    )

    companion object {
        class InputData(
            val selectedItem: ItemUiState?,
            val selectedItemNumber: Int,
            val itemUiStateList: List<ItemUiState>
        )
    }
}

fun ItemDomain?.toItemUiState(): ItemUiState? = if (this == null) {
    null
} else {
    ItemUiState(
        name = name ?: "",
        imageName = imageName ?: "",
        groupType = groupType.toGroupType(),
        buildingMaterials = buildMaterials.map { buildMaterial ->
            BuildingMaterialUiState(
                name = buildMaterial.name,
                groupType = buildMaterial.groupType.toGroupType(),
                count = buildMaterial.count,
            )
        }.toPersistentList()
    )
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
    ItemGroupType.TIER1 -> listOf(ItemGroupType.BASIC_MATERIAL).reversed()
    ItemGroupType.TIER2 -> listOf(ItemGroupType.BASIC_MATERIAL, ItemGroupType.TIER1).reversed()
    ItemGroupType.TIER3 -> listOf(ItemGroupType.BASIC_MATERIAL, ItemGroupType.TIER1, ItemGroupType.TIER2).reversed()
    ItemGroupType.TIER4 -> listOf(ItemGroupType.BASIC_MATERIAL, ItemGroupType.TIER1, ItemGroupType.TIER2, ItemGroupType.TIER3).reversed()
    else -> emptyList()
}

fun getBuildGroupTypeList(): List<ItemGroupType> = listOf(
    ItemGroupType.BASIC_MATERIAL,
    ItemGroupType.TIER1,
    ItemGroupType.TIER2,
    ItemGroupType.TIER3,
    ItemGroupType.TIER4
).sortedBy { it.ordinal }

