package com.sbajt.matscounter.ui.composables.previewProviders

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.sbajt.matscounter.ui.models.ItemGroupType
import com.sbajt.matscounter.ui.models.views.BuildMaterialListWrapper
import com.sbajt.matscounter.ui.models.views.BuildMaterialUiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList

class BuildMaterialUiStateProvider : PreviewParameterProvider<BuildMaterialUiState> {

    override val values: Sequence<BuildMaterialUiState> = sequenceOf(
        basicMaterialUiState,
        materialTier1UiState,
        materialTier2UiState,
        materialTier3UiState,
        materialTier4UiState,

        )

    companion object {

        val basicMaterialUiState = BuildMaterialUiState(
            name = "Basic Material",
            amount = 5,
        )
        val materialTier1UiState = BuildMaterialUiState(
            name = "Material Tier 1",
            amount = 5,
        )
        val materialTier2UiState = BuildMaterialUiState(
            name = "Material Tier 2",
            amount = 5,
        )
        val materialTier3UiState = BuildMaterialUiState(
            name = "Material Tier 3",
            amount = 5,
        )
        val materialTier4UiState = BuildMaterialUiState(
            name = "Material Tier 4",
            amount = 5,
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
    }
}
