package com.sbajt.matscounter.ui.composables

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.sbajt.matscounter.ui.mappers.getItemGroupTypeList
import com.sbajt.matscounter.ui.models.BuildMaterialListWrapper
import com.sbajt.matscounter.ui.models.ItemBuildPathUiState
import com.sbajt.matscounter.ui.models.ItemGroupType
import kotlinx.collections.immutable.toPersistentList

class ItemBuildPathUiStateProvider : PreviewParameterProvider<ItemBuildPathUiState> {

    override val values: Sequence<ItemBuildPathUiState> = sequenceOf(
        defaultBuildPathUiState
    )

    companion object {

        val defaultBuildPathUiState: ItemBuildPathUiState = ItemBuildPathUiState(
            selectedItem = ItemUiStateProvider.tier1ItemUiState,
            selectedItemAmount = 1,
            buildPathList = List(3) { index ->
               val groupType = getItemGroupTypeList()[index % getItemGroupTypeList().size]
                BuildMaterialListWrapper(
                    titleText = groupType.name,
                    buildMaterialsList = ItemUiStateProvider.mockBuildMaterials(count = 3).toPersistentList()
                )
            }
        )
    }
}
