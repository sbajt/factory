package com.sbajt.matscounter.ui.mappers

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

    fun mapToUiState(inputData: InputData): MainScreenUiState = with(inputData) {
        MainScreenUiState.Content(
            itemUiStateList = itemUiStateList.toImmutableList(),
            itemDetailsUiState = selectedItem?.let { selectedItem ->
                ItemDetailsScreenUiState(
                    selectedItem = selectedItem,
                    selectedItemAmount = selectedItemAmount,
                    selectedItemBuildMaterialListWrapper = selectedItem.buildMaterialListWrapper?.copy(
                        titleText = "Build materials",
                    )
                )
            },
            itemBuildPathUiState = selectedItem?.let {
                ItemBuildPathUiState(
                    selectedItem = it,
                    selectedItemAmount = selectedItemAmount,
                    buildPathList = createBuildPathList(
                        selectedItem = it,
                        selectedItemAmount = selectedItemAmount,
                        itemUiStateList = itemUiStateList
                    )
                )
            }
        )
    }

    private fun createBuildPathList(
        selectedItem: ItemUiState,
        selectedItemAmount: Int,
        itemUiStateList: List<ItemUiState>,
    ): List<BuildMaterialListWrapper> {
        val lowerItemGroupTypeList = selectedItem.groupType.toLowerGroupsList().toMutableList()
        val currentBuildMaterialList = mutableListOf<BuildMaterialUiState>()
        return lowerItemGroupTypeList.mapIndexed { index, groupType ->
            val groupBuildMaterialList = mutableListOf<BuildMaterialUiState>()
            processBuildMaterialList(
                validGroupTypeList = lowerItemGroupTypeList - groupType,
                multiplier = selectedItemAmount,
                groupBuildMaterialList = groupBuildMaterialList,
                itemBuildMaterialList = if (index == 0) {
                    selectedItem.buildMaterialListWrapper?.buildMaterialsList ?: emptyList()
                } else {
                    currentBuildMaterialList
                },
                itemUiStateList = itemUiStateList
            )
            groupBuildMaterialList.forEach { material ->
                currentBuildMaterialList.addToBuildMaterialList(material = material, multiplier = material.amount)
            }
            BuildMaterialListWrapper(
                titleText = groupType.getName(),
                groupType = groupType,
                buildMaterialsList = groupBuildMaterialList.toPersistentList(),
            )
        }
    }

    private fun processBuildMaterialList(
        validGroupTypeList: List<ItemGroupType>,
        multiplier: Int,
        groupBuildMaterialList: MutableList<BuildMaterialUiState>,
        itemBuildMaterialList: List<BuildMaterialUiState>,
        itemUiStateList: List<ItemUiState>,
    ) {
        itemBuildMaterialList.map { material ->
            val materialItemUiState = itemUiStateList.firstOrNull { it.name == material.name }
            if (materialItemUiState?.groupType.isValid(validGroupTypeList = validGroupTypeList)) {
                groupBuildMaterialList.addToBuildMaterialList(
                    material = material,
                    multiplier = multiplier,
                )
            }
        }
    }

    private fun ItemGroupType?.isValid(validGroupTypeList: List<ItemGroupType>) = validGroupTypeList.contains(this)

    fun MutableList<BuildMaterialUiState>.addToBuildMaterialList(
        material: BuildMaterialUiState,
        multiplier: Int,
    ) {
        val existingMaterial = this.find { it.name == material.name }
        if (existingMaterial != null) {
            val index = indexOf(existingMaterial)
            this[index] = existingMaterial.copy(amount = existingMaterial.amount + (material.amount * multiplier))
        } else {
            add(material.copy(amount = material.amount * multiplier))
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

private fun ItemGroupType.toLowerGroupType(): ItemGroupType = when (this) {
    ItemGroupType.TIER1 -> ItemGroupType.BASIC_MATERIAL
    ItemGroupType.TIER2 -> ItemGroupType.TIER1
    ItemGroupType.TIER3 -> ItemGroupType.TIER2
    ItemGroupType.TIER4 -> ItemGroupType.TIER3
    else -> this
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

fun ItemGroupType.toLowerGroupsList(): List<ItemGroupType> = when (this) {
    ItemGroupType.TIER1 -> listOf(ItemGroupType.BASIC_MATERIAL)
    ItemGroupType.TIER2 -> listOf(ItemGroupType.TIER1, ItemGroupType.BASIC_MATERIAL)
    ItemGroupType.TIER3 -> listOf(ItemGroupType.TIER2, ItemGroupType.TIER1, ItemGroupType.BASIC_MATERIAL)
    ItemGroupType.TIER4 -> listOf(ItemGroupType.TIER3, ItemGroupType.TIER2, ItemGroupType.TIER1, ItemGroupType.BASIC_MATERIAL)
    else -> emptyList()
}

fun getGroupTypeList(): List<ItemGroupType> = listOf(
    ItemGroupType.BASIC_MATERIAL,
    ItemGroupType.TIER1,
    ItemGroupType.TIER2,
    ItemGroupType.TIER3,
    ItemGroupType.TIER4
)

