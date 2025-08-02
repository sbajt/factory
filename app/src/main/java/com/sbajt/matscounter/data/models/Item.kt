package com.sbajt.matscounter.data.models

import com.google.gson.annotations.SerializedName

data class Item(
    @SerializedName("name")
    val name: String?,
    @SerializedName("imageRes")
    val imageName: String?,
    @SerializedName("groupType")
    val groupType: Int?,
    @SerializedName("priceBuy")
    val priceBuy: Float?,
    @SerializedName("priceSell")
    val priceSell: Float?,
    @SerializedName("buildMaterials")
    val buildMaterials: List<BuildingMaterial>,
)

data class BuildingMaterial(
    @SerializedName("name")
    val name: String?,
    @SerializedName("groupType")
    val groupType: Int?,
    @SerializedName("count")
    val count: Int,
)
