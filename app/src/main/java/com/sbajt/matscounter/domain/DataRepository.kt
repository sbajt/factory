package com.sbajt.matscounter.domain

import com.sbajt.matscounter.domain.models.ItemDomain

interface DataRepository {

    fun getData(): List<ItemDomain>
}