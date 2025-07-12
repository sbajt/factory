package com.sbajt.matscounter.data.gson

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import com.sbajt.matscounter.data.models.ItemGroupType

internal class ItemGroupTypeAdapter : TypeAdapter<ItemGroupType>() {

    override fun write(out: JsonWriter?, heroType: ItemGroupType?) {
        out?.beginObject()
        when (heroType) {
            ItemGroupType.NONE -> out?.value(0)
            ItemGroupType.BASIC_MATERIAL -> out?.value(1)
            ItemGroupType.TIER1 -> out?.value(2)
            ItemGroupType.TIER2 -> out?.value(3)
            ItemGroupType.TIER3 -> out?.value(4)
            ItemGroupType.TIER4 -> out?.value(5)
            ItemGroupType.BUILDER_TIER1 -> out?.value(6)
            ItemGroupType.BUILDER_TIER2 -> out?.value(7)
            ItemGroupType.BUILDER_TIER3 -> out?.value(8)
            ItemGroupType.BUILDER_TIER4 -> out?.value(9)
            ItemGroupType.BUILDER_TASKERS -> out?.value(10)
            ItemGroupType.BUILDER_BUILDERS -> out?.value(11)
            ItemGroupType.BUILDER_UNIVERSAL -> out?.value(12)
            ItemGroupType.TASKER_BUY -> out?.value(13)
            ItemGroupType.TASKER_SELL -> out?.value(14)
            ItemGroupType.TASKER_DELIVERY -> out?.value(15)
            else -> out?.value(0)
        }
        out?.endObject()
    }

    override fun read(`in`: JsonReader?) = when (`in`?.nextInt()) {
        0 -> ItemGroupType.NONE
        1 -> ItemGroupType.BASIC_MATERIAL
        2 -> ItemGroupType.TIER1
        3 -> ItemGroupType.TIER2
        4 -> ItemGroupType.TIER3
        5 -> ItemGroupType.TIER4
        6 -> ItemGroupType.BUILDER_TIER1
        7 -> ItemGroupType.BUILDER_TIER2
        8 -> ItemGroupType.BUILDER_TIER3
        9 -> ItemGroupType.BUILDER_TIER4
        10 -> ItemGroupType.BUILDER_TASKERS
        11 -> ItemGroupType.BUILDER_BUILDERS
        12 -> ItemGroupType.BUILDER_UNIVERSAL
        13 -> ItemGroupType.TASKER_BUY
        14 -> ItemGroupType.TASKER_SELL
        15 -> ItemGroupType.TASKER_DELIVERY
        else -> null
    }
}
