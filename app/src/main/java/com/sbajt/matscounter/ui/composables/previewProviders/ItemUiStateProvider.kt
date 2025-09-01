package com.sbajt.matscounter.ui.composables.previewProviders

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.sbajt.matscounter.ui.mappers.getGroupTypeList
import com.sbajt.matscounter.ui.models.ItemGroupType
import com.sbajt.matscounter.ui.models.views.ItemUiState
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
            buildMaterialListWrapper = BuildMaterialUiStateProvider.mockBuildMaterialWrapper(
                groupType = ItemGroupType.BASIC_MATERIAL,
                buildMaterialsCount = 5,
            ),
        )

        val tier2ItemUiState = ItemUiState(
            name = "Item Tier 2",
            imageName = null,
            groupType = ItemGroupType.TIER2,
            buildMaterialListWrapper = BuildMaterialUiStateProvider.mockBuildMaterialWrapper(
                groupType = ItemGroupType.TIER1,
                buildMaterialsCount = 5,
            ),
        )

        fun mockItemUiStateList(
            count: Int = 10
        ): ImmutableList<ItemUiState> = List(count) {
            val groupType = getGroupTypeList().random()
            ItemUiState(
                name = "Item ${it + 1}",
                imageName = null,
                groupType = groupType,
                buildMaterialListWrapper = BuildMaterialUiStateProvider.mockBuildMaterialWrapper(
                    groupType = groupType,
                    buildMaterialsCount = Random.nextInt(1, 6)
                )
            )
        }.toPersistentList()
    }
}
