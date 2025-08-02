package com.sbajt.matscounter.ui.models

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class ItemDetailsScreenUiState(
    val selectedItem: ItemUiState? = null,
    val selectedItemCount: Int = 0,
    val itemUiStatList: List<ItemUiState> = persistentListOf(),
    val itemBuildingMaterialList: List<BuildingMaterialUiState> = persistentListOf(),
    val itemBuildMaterialsUiState: BuildMaterialListUiState? = null,
    val itemBasicBuildMaterialsUiState: BuildMaterialListUiState? = null,
)

data class InputSectionUiState(
    val selectedItem: ItemUiState? = null,
    val itemCount: Int = 0,
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

data class ItemBuildPathUiState(
    val selectedItem: ItemUiState? = null,
    val selectedItemCount: Int = 0,
    val buildPath: Map<ItemGroupType, ImmutableList<BuildingMaterialUiState>>,
    val buildMaterialListUiState: BuildMaterialListUiState,
)
