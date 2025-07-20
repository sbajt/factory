package com.sbajt.matscounter.ui.mappers

import com.sbajt.matscounter.domain.models.ItemDomain
import com.sbajt.matscounter.ui.models.BuildingMaterialUiState
import com.sbajt.matscounter.ui.models.DescriptionSectionUiState
import com.sbajt.matscounter.ui.models.InputSectionUiState
import com.sbajt.matscounter.ui.models.ItemGroupType
import com.sbajt.matscounter.ui.models.ItemUiState
import com.sbajt.matscounter.ui.models.MainScreenUiState
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toPersistentList

class MainScreenMapper {


    fun mapToUiState(inputData: InputData): MainScreenUiState = with(inputData) {
        MainScreenUiState.Content(
            descriptionUiState = DescriptionSectionUiState(
                selectedItem = selectedItem,
                selectedItemCount = selectedItemCount,
            ),
            inputSectionUiState = InputSectionUiState(
                selectedItem = selectedItem,
                itemCount = selectedItemCount
            ),
            itemUiStateList = itemDomainList
                .map { it.toUiState() }
                .sortedWith(compareBy({ it.groupType }, { it.name }))
                .toImmutableList()
        )
    }

    companion object {
        class InputData(
            val selectedItem: ItemUiState?,
            val selectedItemCount: Int,
            val itemDomainList: List<ItemDomain>
        )
    }
}

fun ItemDomain?.toUiState() = ItemUiState(
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

fun ItemGroupType?.mapToName() = this
    ?.takeIf { it != ItemGroupType.NONE }
    ?.name
    ?.lowercase()
    ?.replaceFirstChar { it.uppercase() }
    ?.replace("_", " ")
    ?: ""



