package com.sbajt.matscounter.data.models

import com.google.gson.annotations.SerializedName

internal data class Item(
    @SerializedName("name")
    val name: String?,
    @SerializedName("imageRes")
    val imageName: String?,
    @SerializedName("groupType")
    val groupType: ItemGroupType?,
    @SerializedName("priceBuy")
    val priceBuy: Float?,
    @SerializedName("priceSell")
    val priceSell: Float?,
    @SerializedName("buildMaterials")
    val buildMaterials: List<Int> = emptyList(),
)

enum class ItemGroupType {
    BASIC_MATERIAL,
    TIER1,
    TIER2,
    TIER3,
    TIER4,
    BUILDER_BUILDERS,
    BUILDER_TASKERS,
    BUILDER_UNIVERSAL,
    BUILDER_TIER1,
    BUILDER_TIER2,
    BUILDER_TIER3,
    BUILDER_TIER4,
    TASKER_BUY,
    TASKER_SELL,
    TASKER_DELIVERY,
    NONE
}
