package com.sbajt.matscounter.ui.navigation

import kotlinx.serialization.Serializable
import org.koin.ext.getFullName

@Serializable
object ItemList

@Serializable
object ItemDetails {
    val ROUTE: String = ItemDetails::class.getFullName()
}

@Serializable
object ItemBuildComponents

