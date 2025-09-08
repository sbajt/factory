package com.sbajt.matscounter.ui.composables.previewProviders

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.sbajt.matscounter.ui.models.ItemGroupType
import com.sbajt.matscounter.ui.models.getName
import com.sbajt.matscounter.ui.models.screens.ItemBuildPathScreenUiState
import com.sbajt.matscounter.ui.models.toLowerGroupsList
import com.sbajt.matscounter.ui.models.views.BuildMaterialListWrapper

class ItemBuildPathUiStateProvider : PreviewParameterProvider<ItemBuildPathScreenUiState> {

    override val values: Sequence<ItemBuildPathScreenUiState> = sequenceOf(
        defaultBuildPathUiState
    )

    companion object {

        val defaultBuildPathUiState: ItemBuildPathScreenUiState =
            ItemBuildPathScreenUiState.Content(
                selectedItem = ItemUiStateProvider.Companion.tier1ItemUiState,
                selectedItemAmount = 1,
                selectedItemBuildMaterialListWrapperList = mockItemBuildMaterialListWrapperList(
                    selectedItemGroupType = ItemUiStateProvider.Companion.tier1ItemUiState.groupType
                )
            )

        fun mockItemBuildMaterialListWrapperList(
            selectedItemGroupType: ItemGroupType
        ): List<BuildMaterialListWrapper> =
            selectedItemGroupType.toLowerGroupsList().map { groupType ->
                BuildMaterialListWrapper(
                    titleText = groupType.getName(),
                    groupType = groupType,
                    buildMaterialsList = BuildMaterialUiStateProvider.mockBuildMaterials(),
                )
            }
    }
}

