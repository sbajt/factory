package com.sbajt.matscounter.domain.models

import com.sbajt.matscounter.data.models.ItemGroupType

data class ItemDomain(
    val name: String?,
    val imageName: String?,
    val groupType: ItemGroupType?,
    val priceBuy: Float?,
    val priceSell: Float?,
    val buildMaterials: List<Int> = emptyList(),
)
