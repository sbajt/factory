package com.sbajt.matscounter.domain.repositories

import com.sbajt.matscounter.domain.models.ItemDomain

interface DataRepository {

    fun getData(): List<ItemDomain>
}
