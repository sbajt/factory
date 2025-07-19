package com.sbajt.matscounter.domain.repositories

import com.sbajt.matscounter.data.DataProvider
import com.sbajt.matscounter.domain.models.ItemDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class ItemsRepositoryImpl(
    private val dataProvider: DataProvider
) : ItemsRepository {

    override fun getData(): Flow<List<ItemDomain>> = flowOf(
        dataProvider
            .getData()
            .map { item ->
                ItemDomain(
                    name = item.name,
                    imageName = item.imageName,
                    groupType = item.groupType,
                    priceBuy = item.priceBuy,
                    priceSell = item.priceSell,
                )
            }
    )

    override fun getItemByName(name: String): ItemDomain? {
        return dataProvider
            .getItemByName(name)
            ?.let { item ->
                ItemDomain(
                    name = item.name,
                    imageName = item.imageName,
                    groupType = item.groupType,
                    priceBuy = item.priceBuy,
                    priceSell = item.priceSell,
                )
            }
    }
}
