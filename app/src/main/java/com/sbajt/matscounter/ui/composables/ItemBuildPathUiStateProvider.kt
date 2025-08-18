package com.sbajt.matscounter.ui.composables

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.sbajt.matscounter.ui.mappers.getGroupTypeList
import com.sbajt.matscounter.ui.models.BuildMaterialListWrapper
import com.sbajt.matscounter.ui.models.ItemBuildPathUiState
import com.sbajt.matscounter.ui.models.ItemGroupType
import kotlinx.collections.immutable.toPersistentList
import kotlin.collections.map

class ItemBuildPathUiStateProvider : PreviewParameterProvider<ItemBuildPathUiState> {

    override val values: Sequence<ItemBuildPathUiState> = sequenceOf(
        defaultBuildPathUiState
    )

    companion object {

        val defaultBuildPathUiState: ItemBuildPathUiState = ItemBuildPathUiState(
            selectedItem = ItemUiStateProvider.tier1ItemUiState,
            selectedItemAmount = 1,
            buildPathList = List(4) { index ->
                val groupType = getGroupTypeList().getOrNull(index) ?: ItemGroupType.BASIC_MATERIAL
                BuildMaterialListWrapper(
                    titleText = groupType.name,
                    groupType = groupType,
                    buildMaterialsList = ItemUiStateProvider.mockBuildMaterials(count = 3).toPersistentList()
                )
            }
        )
    }
}
