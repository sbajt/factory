package com.sbajt.matscounter.ui.mappers

import com.sbajt.matscounter.domain.models.ItemDomain
import com.sbajt.matscounter.ui.models.DescriptionSectionUiState
import com.sbajt.matscounter.ui.models.InputSectionUiState
import com.sbajt.matscounter.ui.models.ItemGroupType
import com.sbajt.matscounter.ui.models.ItemUiState
import com.sbajt.matscounter.ui.models.MainUiState
import kotlinx.collections.immutable.toImmutableList

class MainScreenMapper {

    fun mapToUiState(inputData: InputData) = with(inputData) {
        MainUiState(
            descriptionUiState = DescriptionSectionUiState(
                selectedItem = selectedItem
            ),
            inputSectionUiState = InputSectionUiState(
                selectedItem = selectedItem,
                itemCount = selectedItemCount
            ),
            itemUiStateList = itemDomainList.map { it.toUiState() }.toImmutableList()
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

fun ItemDomain.toUiState() = ItemUiState(
    name = this.name,
    imageName = this.imageName,
    groupType = this.groupType.mapToGroupType(),
)

fun Int?.mapToGroupType(): ItemGroupType? = when (this) {
    0 -> ItemGroupType.NONE
    1 -> ItemGroupType.BASIC_MATERIAL
    2 -> ItemGroupType.TIER1
    3 -> ItemGroupType.TIER2
    4 -> ItemGroupType.TIER3
    5 -> ItemGroupType.TIER4
    6 -> ItemGroupType.BUILDER_TIER1
    7 -> ItemGroupType.BUILDER_TIER2
    8 -> ItemGroupType.BUILDER_TIER3
    9 -> ItemGroupType.BUILDER_TIER4
    10 -> ItemGroupType.BUILDER_TASKERS
    11 -> ItemGroupType.BUILDER_BUILDERS
    12 -> ItemGroupType.BUILDER_UNIVERSAL
    13 -> ItemGroupType.TASKER_BUY
    14 -> ItemGroupType.TASKER_SELL
    15 -> ItemGroupType.TASKER_DELIVERY
    else -> null
}

