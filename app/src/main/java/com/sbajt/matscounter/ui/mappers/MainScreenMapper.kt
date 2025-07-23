package com.sbajt.matscounter.ui.mappers

import com.sbajt.matscounter.domain.models.ItemDomain
import com.sbajt.matscounter.ui.models.BuildingMaterialUiState
import com.sbajt.matscounter.ui.models.DescriptionSectionUiState
import com.sbajt.matscounter.ui.models.InputSectionUiState
import com.sbajt.matscounter.ui.models.ItemGroupType
import com.sbajt.matscounter.ui.models.ItemUiState
import com.sbajt.matscounter.ui.models.MainScreenUiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toPersistentList

class MainScreenMapper {

    fun mapToUiState(inputData: InputData): MainScreenUiState = with(inputData) {
        MainScreenUiState.Content(
            descriptionUiState = DescriptionSectionUiState(
                selectedItem = selectedItem,
                selectedItemCount = selectedItemCount,
                itemUiStatList = itemDomainList.toItemUiStateList(),
                buildingMaterialList = selectedItem.toItemBuildingMaterialList(
                    itemDomainList = itemDomainList,
                )
            ),
            inputSectionUiState = InputSectionUiState(
                selectedItem = selectedItem,
                itemCount = selectedItemCount
            ),
            itemUiStateList = itemDomainList
                .mapNotNull { it.toItemUiState() }
                .sortedWith(compareBy({ it.groupType }, { it.name }))
                .toImmutableList()
        )
    }

    private fun Collection<ItemDomain>.toItemUiStateList(): ImmutableList<ItemUiState> = mapNotNull {
        it.toItemUiState()
    }.toImmutableList()

    private fun ItemUiState?.toItemBuildingMaterialList(
        itemDomainList: List<ItemDomain>,
    ): ImmutableList<BuildingMaterialUiState> {
        val basicBuildingMaterialMap: MutableMap<String, Int> = mutableMapOf()
        if (this != null) {
            processBuildingMaterials(
                buildingMaterials = this.buildingMaterials,
                itemDomainList = itemDomainList,
                basicBuildingMaterialsMap = basicBuildingMaterialMap,
                multiplier = 1,
            )
        }
        return basicBuildingMaterialMap
            .map {
                BuildingMaterialUiState(
                    name = it.key,
                    count = it.value
                )
            }
            .sortedBy { it.name }
            .toPersistentList()
    }

    fun processBuildingMaterials(
        buildingMaterials: List<BuildingMaterialUiState>,
        itemDomainList: List<ItemDomain>,
        basicBuildingMaterialsMap: MutableMap<String, Int>,
        multiplier: Int,
    ) {
        buildingMaterials.map {
            val itemUiState = itemDomainList.find { item -> item.name == it.name }.toItemUiState()
            if (itemUiState != null && it.name != null) {
                val buildingMaterialCount = basicBuildingMaterialsMap.getOrDefault(it.name, 0)
                if (itemUiState.groupType == ItemGroupType.BASIC_MATERIAL) {
                    basicBuildingMaterialsMap[it.name] = buildingMaterialCount + multiplier * it.count
                } else {
                    processBuildingMaterials(
                        buildingMaterials = itemUiState.buildingMaterials.toPersistentList(),
                        itemDomainList = itemDomainList,
                        basicBuildingMaterialsMap = basicBuildingMaterialsMap,
                        multiplier = multiplier * it.count,
                    )
                }
            }
        }
    }

    companion object {
        class InputData(
            val selectedItem: ItemUiState?,
            val selectedItemCount: Int,
            val itemDomainList: List<ItemDomain>
        )
    }
}

fun ItemDomain?.toItemUiState(): ItemUiState? = if (this == null) {
    null
} else {
    ItemUiState(
        name = this.name ?: "",
        imageName = this.imageName ?: "",
        groupType = this.groupType.toGroupType(),
        buildingMaterials = this.buildMaterials.map {
            BuildingMaterialUiState(
                name = it.name,
                count = it.count,
            )
        }.toPersistentList()
    )
}

private fun Int?.toGroupType(): ItemGroupType = ItemGroupType.entries
    .firstOrNull { it.ordinal == this } ?: ItemGroupType.NONE

fun ItemGroupType?.getName() = this
    ?.takeIf { it != ItemGroupType.NONE }
    ?.name
    ?.lowercase()
    ?.replaceFirstChar { it.uppercase() }
    ?.replace("_", " ")
    ?: ""



