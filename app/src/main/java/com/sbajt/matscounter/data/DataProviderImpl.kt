package com.sbajt.matscounter.data

import android.content.Context
import com.sbajt.matscounter.data.models.Item

class DataProviderImpl(override val context: Context) : DataProvider {

    override fun getData(): List<Item> = Cache(assetManager = context.assets).run {
        getBuildingMaterialItemList() +
            getTier1ItemList() +
            getTier2ItemList() +
            getTier3ItemList() +
            getTier4ItemList()
    }
}
