package com.sbajt.matscounter.ui.models

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class ItemDetailsScreenUiState(
    val selectedItem: ItemUiState?,
    val selectedItemAmount: Int,
    val selectedItemBuildMaterialListWrapper: BuildMaterialListWrapper?,
)

data class InputSectionUiState(
    val selectedItem: ItemUiState?,
    val itemCount: Int,
)

data class ItemBuildPathUiState(
    val selectedItem: ItemUiState?,
    val selectedItemAmount: Int,
    val buildPathList: List<BuildMaterialListWrapper>,
)

data class ItemUiState(
    val name: String,
    val imageName: String?,
    val groupType: ItemGroupType,
    val buildMaterialListWrapper: BuildMaterialListWrapper?,
)

data class BuildMaterialUiState(
    val name: String,
    val amount: Int,
)

data class BuildMaterialListWrapper(
    val titleText: String? = null,
    val groupType: ItemGroupType? = null,
    val buildMaterialsList: ImmutableList<BuildMaterialUiState> = persistentListOf(),
)
