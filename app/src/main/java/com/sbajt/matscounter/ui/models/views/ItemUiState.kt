package com.sbajt.matscounter.ui.models.views

import com.sbajt.matscounter.ui.models.ItemGroupType

data class ItemUiState(
    val name: String,
    val imageName: String?,
    val groupType: ItemGroupType,
    val buildMaterialListWrapper: BuildMaterialListWrapper?,
)
