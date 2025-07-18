package com.sbajt.matscounter.domain.repositories

import com.sbajt.matscounter.domain.models.ItemDomain
import kotlinx.coroutines.flow.Flow

interface ItemsRepository {

    fun getData(): Flow<List<ItemDomain>>

    fun getItemByName(name: String): ItemDomain?
}
