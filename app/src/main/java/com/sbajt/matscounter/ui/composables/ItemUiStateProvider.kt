package com.sbajt.matscounter.ui.composables

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.sbajt.matscounter.ui.models.BuildingMaterialUiState
import com.sbajt.matscounter.ui.models.ItemGroupType
import com.sbajt.matscounter.ui.models.ItemUiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import okhttp3.internal.checkOffsetAndCount

class ItemUiStateProvider : PreviewParameterProvider<ItemUiState> {

    override val values: Sequence<ItemUiState> = sequenceOf(
        emptyItemUiState,
        defaultItemUiState,
        basicMaterialItemUiState,
        tier1ItemUiState
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
            name = "Basic Material",
            imageName = "ic_box",
            groupType = ItemGroupType.TIER1,
            buildingMaterials = mockBuildingMaterials(3)
        )

        fun mockBuildingMaterials(
            count: Int = 2,
        ): ImmutableList<BuildingMaterialUiState> = List(count) {
            BuildingMaterialUiState(
                name = "Material $it",
                count = 1,
            )
        }.toPersistentList()

        fun mockItemUiStateList(
            count: Int = 10
        ): ImmutableList<ItemUiState> = List(count) {
            ItemUiState(
                name = "Item $it",
                imageName = "ic_item_$it",
                groupType = ItemGroupType.BASIC_MATERIAL,
                buildingMaterials = mockBuildingMaterials(count = 2)
            )
        }.toPersistentList()
    }
}
