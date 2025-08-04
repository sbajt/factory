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
            itemUiStateList = itemUiStateList
                .sortedWith(compareBy({ it.groupType }, { it.name }))
                .toImmutableList(),
            itemDetailsUiState = inputData.selectedItem?.let { selectedItem ->
                ItemDetailsScreenUiState(
                    selectedItem = selectedItem,
                    selectedItemAmount = selectedItemAmount,
                    selectedItemBuildingMaterialListUiState = createBuildMaterialListWrapper(
                        groupType = selectedItem.groupType,
                        selectedItemAmount = selectedItemAmount,
                        buildingMaterialsList = selectedItem.buildingMaterials
                    ),
                )
            },
            itemBuildPathUiState = inputData.selectedItem?.let {
                ItemBuildPathUiState(
                    selectedItem = it,
                    selectedItemAmount = inputData.selectedItemAmount,
                    buildPathList = createBuildPathList(
                        selectedItem = inputData.selectedItem,
                        selectedItemAmount = inputData.selectedItemAmount,
                    )
                )
            }
        )
    }

    private fun createBuildPathList(
        selectedItem: ItemUiState,
        selectedItemAmount: Int,
    ): List<BuildMaterialListWrapper> = selectedItem
        .groupType
        .toLowerGroupsList()
        .map { groupType ->
            createBuildMaterialListWrapper(
                groupType = groupType,
                selectedItemAmount = selectedItemAmount,
                buildingMaterialsList = selectedItem.buildingMaterials,
            )
        }

    private fun createBuildMaterialListWrapper(
        groupType: ItemGroupType,
        selectedItemAmount: Int,
        buildingMaterialsList: List<BuildingMaterialUiState>,
    ) = BuildMaterialListWrapper(
        titleText = groupType.getName(),
        groupType = groupType,
        selectedItemAmount = selectedItemAmount,
        buildingMaterialsList = buildingMaterialsList.map { material ->
            BuildingMaterialUiState(
                name = material.name,
                groupType = material.groupType,
                count = material.count * selectedItemAmount
            )
        }.toPersistentList()
    )

    companion object {
        class InputData(
            val selectedItem: ItemUiState?,
            val selectedItemAmount: Int,
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

