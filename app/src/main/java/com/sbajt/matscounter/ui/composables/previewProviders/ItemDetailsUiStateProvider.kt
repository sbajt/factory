package com.sbajt.matscounter.ui.composables.previewProviders

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.sbajt.matscounter.ui.models.appBars.AppBarActionType
import com.sbajt.matscounter.ui.models.appBars.AppBarState
import com.sbajt.matscounter.ui.models.screens.ItemDetailsScreenUiState
import com.sbajt.matscounter.ui.models.views.BuildMaterialListWrapper

class ItemDetailsUiStateProvider : PreviewParameterProvider<ItemDetailsScreenUiState> {

    override val values: Sequence<ItemDetailsScreenUiState> = sequenceOf(
        tier1ItemDetailsAppBarScreenUiState,
        tier2ItemDetailsAppBarScreenUiState,
    )

    companion object {
        val tier1ItemDetailsAppBarScreenUiState = ItemDetailsScreenUiState(
            selectedItem = ItemUiStateProvider.Companion.tier2ItemUiState,
            selectedItemAmount = 1,
            selectedItemBuildMaterialListWrapper = BuildMaterialListWrapper(
                titleText = "Build Materials",
                buildMaterialsList = BuildMaterialUiStateProvider.mockBuildMaterials()
            ),
            appBarState = AppBarState.ItemDetailsAppBar(
                title = "Build path",
                actionList = listOf(AppBarActionType.ACTION_BUILD_PATH)
            )
        )
        val tier2ItemDetailsAppBarScreenUiState = ItemDetailsScreenUiState(
            selectedItem = ItemUiStateProvider.Companion.tier2ItemUiState,
            selectedItemAmount = 1,
            selectedItemBuildMaterialListWrapper = BuildMaterialListWrapper(
                titleText = "Build materials",
                buildMaterialsList = BuildMaterialUiStateProvider.mockBuildMaterials()
            ),
            appBarState = AppBarState.ItemDetailsAppBar(
                title = "Build path",
                actionList = listOf(AppBarActionType.ACTION_BUILD_PATH)
            )
        )
    }
}
