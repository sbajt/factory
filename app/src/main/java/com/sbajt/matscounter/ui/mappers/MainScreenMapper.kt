package com.sbajt.matscounter.ui.mappers

import android.util.Log
import com.sbajt.matscounter.domain.models.ItemDomain
import com.sbajt.matscounter.ui.models.BuildMaterialListWrapper
import com.sbajt.matscounter.ui.models.BuildMaterialUiState
import com.sbajt.matscounter.ui.models.ItemBuildPathUiState
import com.sbajt.matscounter.ui.models.ItemDetailsScreenUiState
import com.sbajt.matscounter.ui.models.ItemGroupType
import com.sbajt.matscounter.ui.models.ItemUiState
import com.sbajt.matscounter.ui.models.MainScreenUiState
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toPersistentList

class MainScreenMapper {

    private val TAG = this::class.java.simpleName

    fun mapToUiState(inputData: InputData): MainScreenUiState = with(inputData) {
        MainScreenUiState.Content(
            itemUiStateList = itemUiStateList.toImmutableList(),
            itemDetailsUiState = selectedItem?.let { selectedItem ->
                ItemDetailsScreenUiState(
                    selectedItem = selectedItem,
                    selectedItemAmount = selectedItemAmount,
                    selectedItemBuildMaterialListWrapper = selectedItem.buildMaterialListWrapper?.copy(
                        titleText = "Build materials",
                        subtitleText = selectedItem.mapToSubtitleText(
                            itemUiStateList = itemUiStateList,
                        )
                    )
                )
            },
            itemBuildPathUiState = inputData.selectedItem?.let {
                ItemBuildPathUiState(
                    selectedItem = it,
                    selectedItemAmount = inputData.selectedItemAmount,
                    buildPathList = createBuildPathList(
                        selectedItem = it,
                        selectedItemAmount = inputData.selectedItemAmount,
                        itemUiStateList = inputData.itemUiStateList
                    )
                )
            }
        )
    }

    fun ItemUiState.mapToSubtitleText(itemUiStateList: List<ItemUiState>) = this
        .buildMaterialListWrapper
        ?.buildMaterialsList
        ?.mapNotNull { material ->
            itemUiStateList.firstOrNull { itemUiState -> itemUiState.name == material.name }?.groupType
        }
        ?.maxOfOrNull { it.ordinal }
        ?.let { ItemGroupType.entries[it].getName() }
        ?: ""

    private fun createBuildPathList(
        selectedItem: ItemUiState,
        selectedItemAmount: Int,
        itemUiStateList: List<ItemUiState>,
    ): List<BuildMaterialListWrapper> {
        val lowerItemGroupTypeList = selectedItem.groupType.toLowerGroupsList().toMutableList()
        return lowerItemGroupTypeList.map { groupType ->
            val finalBuildMaterialList = mutableListOf<BuildMaterialUiState>()
            processBuildMaterialList(
                groupType = groupType,
                multiplier = selectedItemAmount,
                finalBuildMaterialList = finalBuildMaterialList,
                itemBuildMaterialList = selectedItem.buildMaterialListWrapper?.buildMaterialsList ?: emptyList(),
                itemUiStateList = itemUiStateList
            )

            BuildMaterialListWrapper(
                titleText = groupType.getName(),
                groupType = groupType,
                buildMaterialsList = finalBuildMaterialList.toPersistentList(),
            )
        }
    }

    private fun processBuildMaterialList(
        groupType: ItemGroupType,
        multiplier: Int,
        finalBuildMaterialList: MutableList<BuildMaterialUiState>,
        itemBuildMaterialList: List<BuildMaterialUiState>,
        itemUiStateList: List<ItemUiState>,
    ) {
        itemBuildMaterialList.map { material ->
            val materialItemUiState = itemUiStateList.firstOrNull { it.name == material.name }
            Log.d(TAG, "$groupType")
                if (materialItemUiState?.groupType.isValid(groupType = groupType)) {
                    finalBuildMaterialList.addToBuildMaterialList(
                        material = material,
                        multiplier = multiplier,
                    )
                }
                else {
                    processBuildMaterialList(
                        groupType = materialItemUiState?.groupType ?: ItemGroupType.NONE,
                        multiplier = multiplier * material.amount,
                        finalBuildMaterialList = finalBuildMaterialList,
                        itemBuildMaterialList = materialItemUiState?.buildMaterialListWrapper?.buildMaterialsList ?: emptyList(),
                        itemUiStateList = itemUiStateList
                    )
                }
        }
    }

    private fun ItemGroupType?.isValid(groupType: ItemGroupType) = this == groupType

    private fun MutableList<BuildMaterialUiState>.addToBuildMaterialList(
        material: BuildMaterialUiState,
        multiplier: Int,
    ) {
        if (this.contains(material)) {
            remove(material)
            add(
                material.copy(
                    amount = material.amount + (material.amount * multiplier)
                )
            )
        } else {
            add(material)
        }
    }

    companion object {
        class InputData(
            val selectedItem: ItemUiState?,
            val selectedItemAmount: Int,
            val itemUiStateList: List<ItemUiState>
        )
    }
}

fun ItemDomain?.toItemUiState(): ItemUiState? = if (this != null) {
    val itemGroupType = groupType.toGroupType()
    val lowerGroupType = itemGroupType.toLowerGroupType()
    ItemUiState(
        name = name ?: "",
        imageName = imageName ?: "",
        groupType = itemGroupType,
        buildMaterialListWrapper = BuildMaterialListWrapper(
            titleText = lowerGroupType.getName(),
            groupType = lowerGroupType,
            buildMaterialsList = buildMaterials.map {
                BuildMaterialUiState(
                    name = it.name,
                    amount = it.count,
                )
            }.toPersistentList(),
        )
    )
} else {
    null
}

fun Int?.toGroupType(): ItemGroupType = ItemGroupType.entries
    .firstOrNull { it.ordinal == this } ?: ItemGroupType.NONE

fun ItemGroupType?.getName() = this
    ?.takeIf { it != ItemGroupType.NONE }
    ?.name
    ?.lowercase()
    ?.replaceFirstChar { it.uppercase() }
    ?.replace("_", " ")
    ?: ""

fun ItemGroupType.toLowerGroupType(): ItemGroupType = when (this) {
    ItemGroupType.TIER1 -> ItemGroupType.BASIC_MATERIAL
    ItemGroupType.TIER2 -> ItemGroupType.TIER1
    ItemGroupType.TIER3 -> ItemGroupType.TIER2
    ItemGroupType.TIER4 -> ItemGroupType.TIER3
    else -> this
}

fun ItemGroupType.toLowerGroupsList(): List<ItemGroupType> = when (this) {
    ItemGroupType.TIER1 -> listOf(ItemGroupType.BASIC_MATERIAL)
    ItemGroupType.TIER2 -> listOf(ItemGroupType.TIER1, ItemGroupType.BASIC_MATERIAL)
    ItemGroupType.TIER3 -> listOf(ItemGroupType.TIER2, ItemGroupType.TIER1, ItemGroupType.BASIC_MATERIAL)
    ItemGroupType.TIER4 -> listOf(ItemGroupType.TIER3, ItemGroupType.TIER2, ItemGroupType.TIER1, ItemGroupType.BASIC_MATERIAL)
    else -> emptyList()
}

fun getItemGroupTypeList(): List<ItemGroupType> = listOf(
    ItemGroupType.BASIC_MATERIAL,
    ItemGroupType.TIER1,
    ItemGroupType.TIER2,
    ItemGroupType.TIER3,
    ItemGroupType.TIER4
)

