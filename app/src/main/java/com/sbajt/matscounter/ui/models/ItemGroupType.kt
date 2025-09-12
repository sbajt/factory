package com.sbajt.matscounter.ui.models

enum class ItemGroupType {
    NONE,
    ALL,
    BUILDER_TIER1,
    BUILDER_TIER2,
    BUILDER_TIER3,
    BUILDER_TIER4,
    BUILDER_BUILDERS,
    BUILDER_TASKERS,
    BUILDER_UNIVERSAL,
    TASKER_BUY,
    TASKER_SELL,
    TASKER_DELIVERY,
    BASIC_MATERIAL,
    TIER1,
    TIER2,
    TIER3,
    TIER4;
}

fun Int?.toGroupType(): ItemGroupType = ItemGroupType.entries
    .firstOrNull { it.ordinal == this } ?: ItemGroupType.NONE

fun ItemGroupType?.getName() = this
    ?.takeIf { it != ItemGroupType.NONE }
    ?.name
    ?.lowercase()
    ?.replaceFirstChar { it.uppercase() }
    ?.replace("_", " ")
    ?: ""

fun ItemGroupType?.isGreater(other: ItemGroupType?) = when (this) {
    ItemGroupType.TIER1 -> other == ItemGroupType.BASIC_MATERIAL
    ItemGroupType.TIER2 -> other == ItemGroupType.TIER1 || other == ItemGroupType.BASIC_MATERIAL
    ItemGroupType.TIER3 -> other == ItemGroupType.TIER2 || other == ItemGroupType.TIER1 || other == ItemGroupType.BASIC_MATERIAL
    ItemGroupType.TIER4 -> other == ItemGroupType.TIER3 || other == ItemGroupType.TIER2 || other == ItemGroupType.TIER1 || other == ItemGroupType.BASIC_MATERIAL
    else -> false
}

fun ItemGroupType?.toLowerGroupType(): ItemGroupType = when (this) {
    ItemGroupType.TIER1 -> ItemGroupType.BASIC_MATERIAL
    ItemGroupType.TIER2 -> ItemGroupType.TIER1
    ItemGroupType.TIER3 -> ItemGroupType.TIER2
    ItemGroupType.TIER4 -> ItemGroupType.TIER3
    else -> ItemGroupType.NONE
}

fun ItemGroupType?.toLowerGroupsList(): List<ItemGroupType> = when (this) {
    ItemGroupType.TIER1 -> listOf(ItemGroupType.BASIC_MATERIAL)
    ItemGroupType.TIER2 -> listOf(ItemGroupType.TIER1, ItemGroupType.BASIC_MATERIAL)
    ItemGroupType.TIER3 -> listOf(ItemGroupType.TIER2, ItemGroupType.TIER1, ItemGroupType.BASIC_MATERIAL)

    ItemGroupType.TIER4 -> listOf(
        ItemGroupType.TIER3,
        ItemGroupType.TIER2,
        ItemGroupType.TIER1,
        ItemGroupType.BASIC_MATERIAL
    )

    else -> emptyList()
}

fun getBuildGroupTypeList(): List<ItemGroupType> = listOf(
    ItemGroupType.BASIC_MATERIAL,
    ItemGroupType.TIER1,
    ItemGroupType.TIER2,
    ItemGroupType.TIER3,
    ItemGroupType.TIER4
)
