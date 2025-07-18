package com.sbajt.matscounter.data

import android.content.Context
import com.sbajt.matscounter.data.models.Item

interface DataProvider{

    val context: Context

    fun getData(): List<Item>

    fun getItemByName(name: String): Item?
}
