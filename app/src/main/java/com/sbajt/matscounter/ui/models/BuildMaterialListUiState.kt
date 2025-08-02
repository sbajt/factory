package com.sbajt.matscounter.ui.models

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class BuildMaterialListUiState(
    val titleText: String? = null,
    val selectedItemCount: Int = 0,
    val buildingMaterialsList: ImmutableList<BuildingMaterialUiState> = persistentListOf(),
)
