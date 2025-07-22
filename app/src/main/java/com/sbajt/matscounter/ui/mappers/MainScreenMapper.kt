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
                buildingMaterialList = itemDomainList.toItemBuildingMaterialList(
                    selectedItem = selectedItem,
                    selectedItemCount = selectedItemCount,
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

    private fun List<ItemDomain>.toItemBuildingMaterialList(
        selectedItem: ItemUiState?,
        selectedItemCount: Int,
    ): ImmutableList<BuildingMaterialUiState> {
        if (selectedItem == null) return persistentListOf()
        val buildingMaterialMap = mutableMapOf<String, Int>()
        selectedItem
            .buildingMaterials
            .map { buildingMaterial ->
                val selectedItemDomain = this.find { it.name == selectedItem.name }
                when {
                    selectedItemDomain == null -> return persistentListOf()
                    selectedItemDomain.groupType == ItemGroupType.BASIC_MATERIAL.ordinal -> {
                        if (buildingMaterialMap.contains(selectedItemDomain.name)) {
                            buildingMaterialMap[selectedItemDomain.name.toString()] = selectedItemCount + (buildingMaterialMap[selectedItemDomain.name.toString()] ?: 0)
                        } else {
                            buildingMaterialMap[selectedItemDomain.name.toString()] = selectedItemCount
                        }
                    }
                }

            }
        return buildingMaterialMap.map {
            BuildingMaterialUiState(
                name = it.key,
                count = it.value
            )
        }.toPersistentList()
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
    groupType = this?.groupType.mapToGroupType(),
    buildingMaterials = this?.buildMaterials?.map {
        BuildingMaterialUiState(
            name = it.name,
            count = it.count,
        )
    }?.toPersistentList() ?: persistentListOf()
)

private fun Int?.mapToGroupType(): ItemGroupType = ItemGroupType.entries
    .firstOrNull { it.ordinal == this } ?: ItemGroupType.NONE

fun ItemGroupType?.getName() = this
    ?.takeIf { it != ItemGroupType.NONE }
    ?.name
    ?.lowercase()
    ?.replaceFirstChar { it.uppercase() }
    ?.replace("_", " ")
    ?: ""



