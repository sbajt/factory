package com.sbajt.matscounter.domain.repositories

import android.content.res.AssetManager
import com.sbajt.matscounter.data.DataProvider
import com.sbajt.matscounter.data.models.ItemGroupType
import com.sbajt.matscounter.domain.models.ItemDomain

internal class DataRepository(private val assetManager: AssetManager?) {

    operator fun invoke(): List<ItemDomain> =
        DataProvider(assetManager = assetManager)()
            .map {
                ItemDomain(
                    name = it.name,
                    imageName = it.imageName,
                    groupType = it.groupType,
                    priceBuy = it.priceBuy,
                    priceSell = it.priceSell,
                )
            }

    fun getItemByNameAndType(name: String?, groupType: ItemGroupType?): ItemDomain? {
        return if (name == null || groupType == null) {
            null
        } else {
            invoke().first { it.name == name && it.groupType == groupType }
        }
    }
}
