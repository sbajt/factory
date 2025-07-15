package com.sbajt.matscounter.domain.repositories

import com.sbajt.matscounter.data.DataProvider
import com.sbajt.matscounter.domain.repositories.DataRepository
import com.sbajt.matscounter.domain.models.ItemDomain

class DataRepositoryImpl(
    private val dataProvider: DataProvider
) : DataRepository {

    override fun getData(): List<ItemDomain> = dataProvider
        .getData()
        .map {
            ItemDomain(
                name = it.name,
                imageName = it.imageName,
                groupType = it.groupType,
                priceBuy = it.priceBuy,
                priceSell = it.priceSell,
            )
        }
}
