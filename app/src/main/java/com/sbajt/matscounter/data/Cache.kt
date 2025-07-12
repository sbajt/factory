package com.sbajt.matscounter.data

import android.content.res.AssetManager
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.sbajt.matscounter.data.gson.ItemGroupTypeAdapter
import com.sbajt.matscounter.data.models.Item
import com.sbajt.matscounter.data.models.ItemGroupType
import java.io.BufferedReader
import java.io.InputStreamReader

internal class Cache(private val assetManager: AssetManager?) {

    private val gson = GsonBuilder()
        .registerTypeAdapter(ItemGroupType::class.java, ItemGroupTypeAdapter())
        .create()

    fun getBuildingMaterialItemList(): List<Item> = gson.fromJson<List<Item?>>(
        BufferedReader(InputStreamReader(assetManager?.open("building_materials.json"))),
        object : TypeToken<List<Item?>>() {}.type
    ).filterNotNull()

    fun getTier1ItemList(): List<Item> = gson.fromJson<List<Item?>>(
        BufferedReader(InputStreamReader(assetManager?.open("tier1.json"))),
        object : TypeToken<List<Item?>>() {}.type
    ).filterNotNull()

    fun getTier2ItemList(): List<Item> = gson.fromJson<List<Item?>>(
        BufferedReader(InputStreamReader(assetManager?.open("tier2.json"))),
        object : TypeToken<List<Item?>>() {}.type
    ).filterNotNull()

    fun getTier3ItemList(): List<Item> = gson.fromJson<List<Item?>>(
        BufferedReader(InputStreamReader(assetManager?.open("tier3.json"))),
        object : TypeToken<List<Item?>>() {}.type
    ).filterNotNull()

    fun getTier4ItemList(): List<Item> = gson.fromJson<List<Item?>>(
        BufferedReader(InputStreamReader(assetManager?.open("tier4.json"))),
        object : TypeToken<List<Item?>>() {}.type
    ).filterNotNull()
}
