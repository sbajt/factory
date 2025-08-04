package com.sbajt.matscounter.ui.models

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class ItemDetailsScreenUiState(
    val selectedItem: ItemUiState? = null,
    val selectedItemAmount: Int = 0,
    val selectedItemBuildingMaterialListUiState: BuildMaterialListWrapper? = null,
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
    val buildingMaterials: ImmutableList<BuildingMaterialUiState> = persistentListOf(),
)

data class BuildingMaterialUiState(
    val name: String? = null,
    val groupType: ItemGroupType = ItemGroupType.NONE,
    val count: Int = 0,
)

data class BuildMaterialListWrapper(
    val titleText: String? = null,
    val groupType: ItemGroupType? = null,
    val selectedItemAmount: Int = 0,
    val buildingMaterialsList: ImmutableList<BuildingMaterialUiState> = persistentListOf(),
)
