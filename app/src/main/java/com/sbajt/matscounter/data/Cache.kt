package com.sbajt.matscounter.data

import android.content.res.AssetManager
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.sbajt.matscounter.data.models.Item
import java.io.BufferedReader
import java.io.InputStreamReader

internal class Cache(private val assetManager: AssetManager?) {

    private val gson = GsonBuilder().create()

    fun getBuildingMaterialItemList(): List<Item> = gson.fromJson<List<Item?>>(
        BufferedReader(InputStreamReader(assetManager?.open("json/building_materials.json"))),
        object : TypeToken<List<Item?>>() {}.type
    ).filterNotNull()

    fun getTier1ItemList(): List<Item> = gson.fromJson<List<Item?>>(
        BufferedReader(InputStreamReader(assetManager?.open("json/tier1.json"))),
        object : TypeToken<List<Item?>>() {}.type
    ).filterNotNull()

    fun getTier2ItemList(): List<Item> = gson.fromJson<List<Item?>>(
        BufferedReader(InputStreamReader(assetManager?.open("json/tier2.json"))),
        object : TypeToken<List<Item?>>() {}.type
    ).filterNotNull()

    fun getTier3ItemList(): List<Item> = gson.fromJson<List<Item?>>(
        BufferedReader(InputStreamReader(assetManager?.open("json/tier3.json"))),
        object : TypeToken<List<Item?>>() {}.type
    ).filterNotNull()

    fun getTier4ItemList(): List<Item> = gson.fromJson<List<Item?>>(
        BufferedReader(InputStreamReader(assetManager?.open("json/tier4.json"))),
        object : TypeToken<List<Item?>>() {}.type
    ).filterNotNull()
}
