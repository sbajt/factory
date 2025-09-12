package com.sbajt.matscounter.ui.mappers

import com.sbajt.matscounter.ui.models.ItemGroupType
import com.sbajt.matscounter.ui.models.toLowerGroupType
import com.sbajt.matscounter.ui.models.toLowerGroupsList
import com.sbajt.matscounter.ui.models.views.BuildMaterialListWrapper
import com.sbajt.matscounter.ui.models.views.BuildMaterialUiState
import com.sbajt.matscounter.ui.models.views.ItemUiState

class BuildMaterialListWrapperMapper {

    fun mapToUiState(inputData: InputData): BuildMaterialListWrapper {

        val buildMaterialList = mutableListOf<BuildMaterialUiState>()
        if (inputData.hasOnlyInitialBuildMaterials) {
            buildMaterialList.addAll(inputData.initialBuildMaterialsList)
        } else {
            buildMaterialList.addAll(inputData.initialBuildMaterialsList)
            buildMaterialList.processList(
                buildMaterialList = inputData.initialBuildMaterialsList,
                multiplier = inputData.initialItemAmount,
                groupType = inputData.groupType,
                itemList = inputData.itemList,
            )
        }
        return BuildMaterialListWrapper(
            titleText = inputData.titleText,
            groupType = inputData.groupType,
            buildMaterialsList = buildMaterialList.sortedBy { it.name },
        )
    }

    private fun MutableList<BuildMaterialUiState>.processList(
        buildMaterialList: List<BuildMaterialUiState>,
        multiplier: Int,
        groupType: ItemGroupType? = null,
        itemList: List<ItemUiState> = emptyList(),
    ) {
        buildMaterialList.forEach { buildMaterial ->
            val buildMaterialItem = itemList.find { it.name == buildMaterial.name }
            buildMaterialItem?.let {
                if (buildMaterialItem.groupType.isValid(groupType = groupType)) {
                    addOrUpdateBuildMaterial(buildMaterial = buildMaterial, multiplier = multiplier)
                } else {
                    this.remove(buildMaterial)
                    processList(
                        buildMaterialList = buildMaterialItem.buildMaterialListWrapper?.buildMaterialsList ?: emptyList(),
                        multiplier = buildMaterial.amount * multiplier,
                        groupType = groupType.toLowerGroupType(),
                        itemList = itemList,
                    )
                }
            }
        }
    }

    private fun ItemGroupType?.isValid(groupType: ItemGroupType?) = this == groupType
        || this?.toLowerGroupsList()?.contains(this) == true

    private fun MutableList<BuildMaterialUiState>.addOrUpdateBuildMaterial(
        buildMaterial: BuildMaterialUiState,
        multiplier: Int,
    ) {
        val buildMaterialItem = this.find { it.name == buildMaterial.name }
        buildMaterialItem?.let {
            val existingBuildMaterial = this.find { it.name == buildMaterial.name }
            existingBuildMaterial?.let {
                this.remove(existingBuildMaterial)
                this.add(buildMaterial.copy(amount = existingBuildMaterial.amount + multiplier * buildMaterial.amount))
            }
        }
    }

    companion object {

        data class InputData(
            val titleText: String?,
            val groupType: ItemGroupType?,
            val initialBuildMaterialsList: List<BuildMaterialUiState>,
            val initialItemAmount: Int,
            val itemList: List<ItemUiState>,
            val hasOnlyInitialBuildMaterials: Boolean = false,
        )
    }
}
