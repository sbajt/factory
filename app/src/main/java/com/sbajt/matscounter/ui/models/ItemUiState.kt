package com.sbajt.matscounter.ui.models

data class ItemUiState(
    val name: String?,
    val imageName: String?,
    val groupType: ItemGroupType?,
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
