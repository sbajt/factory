package com.sbajt.matscounter.ui.composables

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.sbajt.matscounter.ui.mappers.getBuildGroupTypeList
import com.sbajt.matscounter.ui.models.BuildingMaterialUiState
import com.sbajt.matscounter.ui.models.ItemGroupType
import com.sbajt.matscounter.ui.models.ItemUiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList

class ItemUiStateProvider : PreviewParameterProvider<ItemUiState> {

    override val values: Sequence<ItemUiState> = sequenceOf(
        emptyItemUiState,
        defaultItemUiState,
        basicMaterialItemUiState,
        tier1ItemUiState,
        tier2ItemUiState,
    )

    companion object {
        val emptyItemUiState = ItemUiState()

        val defaultItemUiState = ItemUiState(
            name = "Item Name",
            groupType = ItemGroupType.BASIC_MATERIAL
        )

        val basicMaterialItemUiState = ItemUiState(
            name = "Basic Material",
            imageName = "ic_cardboard",
            groupType = ItemGroupType.BASIC_MATERIAL
        )

        val tier1ItemUiState = ItemUiState(
            name = "Material",
            imageName = "ic_box",
            groupType = ItemGroupType.TIER1
        )

        val tier2ItemUiState = ItemUiState(
            name = "Material",
            imageName = "ic_box",
            groupType = ItemGroupType.TIER2
        )

        fun mockBuildingMaterials(
            count: Int = 2,
        ): ImmutableList<BuildingMaterialUiState> = List(count) {
            BuildingMaterialUiState(
                name = "Material $it",
                amount = 1,
            )
        }.toPersistentList()

        fun mockItemUiStateList(
            count: Int = 10
        ): ImmutableList<ItemUiState> = List(count) {
            ItemUiState(
                name = "Item $it",
                imageName = "ic_item_$it",
                groupType = getBuildGroupTypeList()[it % getBuildGroupTypeList().size]
            )
        }.toPersistentList()
    }
}
