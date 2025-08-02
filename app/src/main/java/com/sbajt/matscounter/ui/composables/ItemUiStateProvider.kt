package com.sbajt.matscounter.ui.composables

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.sbajt.matscounter.ui.mappers.getBuildGroupTypeList
import com.sbajt.matscounter.ui.mappers.toLowerGroupsList
import com.sbajt.matscounter.ui.models.BuildingMaterialUiState
import com.sbajt.matscounter.ui.models.ItemGroupType
import com.sbajt.matscounter.ui.models.ItemUiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
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
            imageName = "",
            groupType = ItemGroupType.BASIC_MATERIAL,
            buildingMaterials = persistentListOf()
        )

        val basicMaterialItemUiState = ItemUiState(
            name = "Basic Material",
            imageName = "ic_cardboard",
            groupType = ItemGroupType.BASIC_MATERIAL,
            buildingMaterials = persistentListOf()
        )

        val tier1ItemUiState = ItemUiState(
            name = "Material",
            imageName = "ic_box",
            groupType = ItemGroupType.TIER1,
            buildingMaterials = mockBuildingMaterials(
                count = 3,
                lowerGroupTypeList = ItemGroupType.TIER1.toLowerGroupsList()
            )
        )

        val tier2ItemUiState = ItemUiState(
            name = "Material",
            imageName = "ic_box",
            groupType = ItemGroupType.TIER2,
            buildingMaterials = mockBuildingMaterials(
                count = 3,
                lowerGroupTypeList = ItemGroupType.TIER2.toLowerGroupsList()
            )
        )

        fun mockBuildingMaterials(
            count: Int = 2,
            lowerGroupTypeList: List<ItemGroupType>,
        ): ImmutableList<BuildingMaterialUiState> = List(count) {
            BuildingMaterialUiState(
                name = "Material $it",
                count = 1,
                groupType = lowerGroupTypeList.getOrNull(it) ?: ItemGroupType.BASIC_MATERIAL
            )
        }.toPersistentList()

        fun mockItemUiStateList(
            count: Int = 10
        ): ImmutableList<ItemUiState> = List(count) {
            ItemUiState(
                name = "Item $it",
                imageName = "ic_item_$it",
                groupType = getBuildGroupTypeList()[it % getBuildGroupTypeList().size],
                buildingMaterials = mockBuildingMaterials(
                    count = 3,
                    lowerGroupTypeList = ItemGroupType.BASIC_MATERIAL.toLowerGroupsList(
                    )
                )
            )
        }.toPersistentList()
    }
}
