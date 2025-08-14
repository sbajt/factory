package com.sbajt.matscounter.ui.models

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class ItemDetailsScreenUiState(
    val selectedItem: ItemUiState? = null,
    val selectedItemAmount: Int = 0,
    val selectedItemBuildMaterialListWrapper: BuildMaterialListWrapper? = null,
)

data class InputSectionUiState(
    val selectedItem: ItemUiState? = null,
    val itemCount: Int = 0,
)

data class ItemBuildPathUiState(
    val selectedItem: ItemUiState? = null,
    val selectedItemAmount: Int = 0,
    val buildPathList: List<BuildMaterialListWrapper>,
)

data class ItemUiState(
    val name: String = "",
    val imageName: String = "",
    val groupType: ItemGroupType = ItemGroupType.NONE,
    val buildMaterialListWrapper: BuildMaterialListWrapper? = null,
)

data class BuildMaterialUiState(
    val name: String? = null,
    val amount: Int = 0,
)

data class BuildMaterialListWrapper(
    val titleText: String? = null,
    val subtitleText: String? = null,
    val groupType: ItemGroupType? = null,
    val buildMaterialsList: ImmutableList<BuildMaterialUiState> = persistentListOf(),
)
