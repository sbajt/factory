package com.sbajt.matscounter.domain.models

data class ItemDomain(
    val name: String?,
    val imageName: String?,
    val groupType: Int?,
    val priceBuy: Float?,
    val priceSell: Float?,
    val buildMaterials: List<BuildingMaterial>,
)

data class BuildingMaterial(
    val name: String?,
    val groupType: Int?,
    val count: Int,
)

