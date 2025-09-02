package com.sbajt.matscounter.ui.models.views

import com.sbajt.matscounter.ui.models.ItemGroupType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class BuildMaterialListWrapper(
    val titleText: String? = null,
    val groupType: ItemGroupType? = null,
    val buildMaterialsList: List<BuildMaterialUiState> = persistentListOf(),
)
