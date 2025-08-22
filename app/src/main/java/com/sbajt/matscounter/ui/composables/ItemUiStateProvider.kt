package com.sbajt.matscounter.ui.composables

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.sbajt.matscounter.ui.mappers.getGroupTypeList
import com.sbajt.matscounter.ui.models.BuildMaterialListWrapper
import com.sbajt.matscounter.ui.models.BuildMaterialUiState
import com.sbajt.matscounter.ui.models.ItemGroupType
import com.sbajt.matscounter.ui.models.ItemUiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList
import kotlin.random.Random

class ItemUiStateProvider : PreviewParameterProvider<ItemUiState> {

    override val values: Sequence<ItemUiState> = sequenceOf(
        basicMaterialItemUiState,
        tier1ItemUiState,
        tier2ItemUiState,
    )

    companion object {
        val basicMaterialItemUiState = ItemUiState(
            name = "Item Basic",
            imageName = null,
            groupType = ItemGroupType.BASIC_MATERIAL,
            buildMaterialListWrapper = null
        )

        val tier1ItemUiState = ItemUiState(
            name = "Item Tier 1",
            imageName = null,
            groupType = ItemGroupType.TIER1,
            buildMaterialListWrapper = mockBuildMaterialWrapper(
                groupType = ItemGroupType.BASIC_MATERIAL,
                buildMaterialsCount = 5,
            ),
        )

        val tier2ItemUiState = ItemUiState(
            name = "Item Tier 2",
            imageName = null,
            groupType = ItemGroupType.TIER2,
            buildMaterialListWrapper = mockBuildMaterialWrapper(
                groupType = ItemGroupType.TIER1,
                buildMaterialsCount = 5,
            ),
        )

        fun mockBuildMaterialWrapper(
            groupType: ItemGroupType,
            buildMaterialsCount: Int = 5,
        ) = BuildMaterialListWrapper(
            titleText = groupType.name,
            groupType = groupType,
            buildMaterialsList = mockBuildMaterials(buildMaterialsCount)
        )

        fun mockBuildMaterials(
            count: Int = 4,
        ): ImmutableList<BuildMaterialUiState> = List(count) {
            BuildMaterialUiState(
                name = "Material $it",
                amount = 5,
            )
        }.toPersistentList()

        fun mockItemUiStateList(
            count: Int = 10
        ): ImmutableList<ItemUiState> = List(count) {
            val groupType = getGroupTypeList().random()
            ItemUiState(
                name = "Item ${it + 1}",
                imageName = null,
                groupType = groupType,
                buildMaterialListWrapper = mockBuildMaterialWrapper(
                    groupType = groupType,
                    buildMaterialsCount = Random.nextInt(1, 6)
                )
            )
        }.toPersistentList()
    }
}
