package com.sbajt.matscounter.data

import android.content.Context
import com.sbajt.matscounter.data.models.Item

class DataProviderImpl(override val context: Context) : DataProvider {

    private val itemList: MutableList<Item> = mutableListOf()

    override fun getData(): List<Item> = Cache(assetManager = context.assets).run {
        itemList.addAll(
            getBuildMaterialItemList() +
                getTier1ItemList() +
                getTier2ItemList() +
                getTier3ItemList() +
                getTier4ItemList()
        )
        itemList
    }

    override fun getItemByName(name: String): Item? {
        return itemList.find { it.name == name }
    }
}
