package com.sbajt.matscounter.ui.mappers

import com.sbajt.matscounter.domain.models.ItemDomain
import com.sbajt.matscounter.ui.models.BuildingMaterialUiState
import com.sbajt.matscounter.ui.models.DescriptionSectionUiState
import com.sbajt.matscounter.ui.models.InputSectionUiState
import com.sbajt.matscounter.ui.models.ItemGroupType
import com.sbajt.matscounter.ui.models.ItemUiState
import com.sbajt.matscounter.ui.models.MainScreenUiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
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
                .map { it.toItemUiState() }
                .sortedWith(compareBy({ it.groupType }, { it.name }))
                .toImmutableList()
        )
    }

    private fun Collection<ItemDomain>.toItemUiStateList(): ImmutableList<ItemUiState> = map { it.toItemUiState() }.toImmutableList()

    private fun ItemUiState?.toItemBuildingMaterialList(
        itemDomainList: List<ItemDomain>,
    ): ImmutableList<BuildingMaterialUiState> {
        val basicBuildingMaterialsMap: MutableMap<String, Int> = mutableMapOf()
        processBuildingMaterials(
            buildingMaterials = this?.buildingMaterials ?: persistentListOf(),
            itemDomainList = itemDomainList,
            basicBuildingMaterialsMap = basicBuildingMaterialsMap

        )
        return basicBuildingMaterialsMap.map {
            BuildingMaterialUiState(
                name = it.key,
                count = it.value
            )
        }.toPersistentList()
    }

    fun processBuildingMaterials(
        buildingMaterials: List<BuildingMaterialUiState>,
        itemDomainList: List<ItemDomain>,
        basicBuildingMaterialsMap: MutableMap<String, Int>,
    ): Int {
        buildingMaterials.map {
            val itemUiState = itemDomainList.find { item -> item.name == it.name }?.toItemUiState()
            when {
                itemUiState?.groupType == ItemGroupType.BASIC_MATERIAL
                    && it.name != null -> {
                    basicBuildingMaterialsMap.put(
                        key = it.name,
                        value = (basicBuildingMaterialsMap[it.name] ?: 1) * it.count
                    )
                }

                itemUiState?.groupType != null -> {
                    processBuildingMaterials(
                        buildingMaterials = itemUiState.buildingMaterials,
                        itemDomainList = itemDomainList,
                        basicBuildingMaterialsMap = basicBuildingMaterialsMap
                    )
                }

                else -> {
                    return 0
                }
            }
        }
        return 0
    }

    companion object {
        class InputData(
            val selectedItem: ItemUiState?,
            val selectedItemCount: Int,
            val itemDomainList: List<ItemDomain>
        )
    }
}

fun ItemDomain?.toItemUiState() = ItemUiState(
    name = this?.name ?: "",
    imageName = this?.imageName ?: "",
    groupType = this?.groupType.toGroupType(),
    buildingMaterials = this?.buildMaterials?.map {
        BuildingMaterialUiState(
            name = it.name,
            count = it.count,
        )
    }?.toPersistentList() ?: persistentListOf()
)

private fun Int?.toGroupType(): ItemGroupType = ItemGroupType.entries
    .firstOrNull { it.ordinal == this } ?: ItemGroupType.NONE

fun ItemGroupType?.getName() = this
    ?.takeIf { it != ItemGroupType.NONE }
    ?.name
    ?.lowercase()
    ?.replaceFirstChar { it.uppercase() }
    ?.replace("_", " ")
    ?: ""



