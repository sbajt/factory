package com.sbajt.matscounter.ui.mappers

import android.util.DisplayMetrics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.sbajt.matscounter.domain.models.ItemDomain
import com.sbajt.matscounter.ui.models.BuildingMaterialUiState
import com.sbajt.matscounter.ui.models.ItemDetailsScreenUiState
import com.sbajt.matscounter.ui.models.ItemBuildPathUiState
import com.sbajt.matscounter.ui.models.ItemGroupType
import com.sbajt.matscounter.ui.models.ItemUiState
import com.sbajt.matscounter.ui.models.MainScreenUiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toPersistentList

class MainScreenMapper {

    fun mapToUiState(inputData: InputData): MainScreenUiState = with(inputData) {
        MainScreenUiState.Content(
            itemUiStateList = itemDomainList
                .mapNotNull { it.toItemUiState() }
                .sortedWith(compareBy({ it.groupType }, { it.name }))
                .toImmutableList(),
            itemDetailsUiState = inputData.selectedItem?.run {
                ItemDetailsScreenUiState(
                    selectedItem = selectedItem,
                    selectedItemCount = selectedItemCount,
                    itemUiStatList = itemDomainList.toItemUiStateList(),
                    buildingMaterialList = selectedItem.toItemBuildingMaterialList(
                        itemDomainList = itemDomainList,
                    )
                )
            },
            itemBuildPathUiState = inputData.selectedItem?.let {
                ItemBuildPathUiState(
                    selectedItem = it,
                )
            }
        )

    }

    private fun Collection<ItemDomain>.toItemUiStateList(): ImmutableList<ItemUiState> =
        mapNotNull {
            it.toItemUiState()
        }.toImmutableList()

    private fun ItemUiState?.toItemBuildingMaterialList(
        itemDomainList: List<ItemDomain>,
    ): ImmutableList<BuildingMaterialUiState> {
        val basicBuildingMaterialMap: MutableMap<String, Int> = mutableMapOf()
        if (this != null) {
            processBuildingMaterials(
                buildingMaterials = this.buildingMaterials,
                itemDomainList = itemDomainList,
                basicBuildingMaterialsMap = basicBuildingMaterialMap,
            )
        }
        return basicBuildingMaterialMap
            .map {
                BuildingMaterialUiState(
                    name = it.key,
                    count = it.value
                )
            }
            .sortedBy { it.name }
            .toPersistentList()
    }

    private fun processBuildingMaterials(
        buildingMaterials: List<BuildingMaterialUiState>,
        itemDomainList: List<ItemDomain>,
        limitGroupType: ItemGroupType = ItemGroupType.BASIC_MATERIAL,
        basicBuildingMaterialsMap: MutableMap<String, Int>,
        multiplier: Int = 1,
    ) {
        buildingMaterials.forEach { buildingMaterial ->
            val itemUiState =
                itemDomainList.find { item -> item.name == buildingMaterial.name }?.toItemUiState()
                    ?: return@forEach

            if (itemUiState.groupType == limitGroupType && buildingMaterial.name != null) {
                basicBuildingMaterialsMap[buildingMaterial.name] =
                    (multiplier * buildingMaterial.count) +
                            (basicBuildingMaterialsMap[buildingMaterial.name] ?: 0)
            } else if (itemUiState.groupType > limitGroupType) {
                processBuildingMaterials(
                    buildingMaterials = itemUiState.buildingMaterials,
                    itemDomainList = itemDomainList,
                    limitGroupType = limitGroupType,
                    basicBuildingMaterialsMap = basicBuildingMaterialsMap,
                    multiplier = multiplier * buildingMaterial.count,
                )
            }
        }
    }

    companion object {
        class InputData(
            val selectedItem: ItemUiState?,
            val selectedItemCount: Int,
            val itemDomainList: List<ItemDomain>
        )
    }
}

fun ItemDomain?.toItemUiState(): ItemUiState? = if (this == null) {
    null
} else {
    ItemUiState(
        name = this.name ?: "",
        imageName = this.imageName ?: "",
        groupType = this.groupType.toGroupType(),
        buildingMaterials = this.buildMaterials.map {
            BuildingMaterialUiState(
                name = it.name,
                count = it.count,
            )
        }.toPersistentList()
    )
}

private fun Int?.toGroupType(): ItemGroupType = ItemGroupType.entries
    .firstOrNull { it.ordinal == this } ?: ItemGroupType.NONE

fun ItemGroupType?.getName() = this
    ?.takeIf { it != ItemGroupType.NONE }
    ?.name
    ?.lowercase()
    ?.replaceFirstChar { it.uppercase() }
    ?.replace("_", " ")
    ?: ""

fun Int?.pxToDp(displayMetrics: DisplayMetrics): Dp = this?.let {
    (it / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT)).dp
} ?: 0.dp

