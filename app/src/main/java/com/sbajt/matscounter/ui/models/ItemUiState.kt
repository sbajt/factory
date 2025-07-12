package com.sbajt.matscounter.ui.models

import com.sbajt.matscounter.data.models.ItemGroupType

data class ItemUiState(
    val name: String?,
    val imageName: String?,
    val groupType: ItemGroupType?,
)
