package com.sbajt.matscounter.data

import android.content.res.AssetManager
import com.sbajt.matscounter.data.models.Item

internal class DataProvider(private val assetManager: AssetManager?) {

    operator fun invoke(): List<Item> = Cache(assetManager = assetManager).run {
        getBuildingMaterialItemList() +
            getTier1ItemList() +
            getTier2ItemList() +
            getTier3ItemList() +
            getTier4ItemList()
    }
}
