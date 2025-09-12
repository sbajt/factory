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
        processList(
            outputBuildMaterialList = buildMaterialList,
            buildMaterialList = inputData.initialBuildMaterialsList,
            initialItemAmount = inputData.initialItemAmount,
            groupType = inputData.groupType,
            itemList = inputData.itemList,
        )
        return BuildMaterialListWrapper(
            titleText = inputData.titleText,
            groupType = inputData.groupType,
            buildMaterialsList = buildMaterialList.sortedBy { it.name },
        )
    }

    private fun processList(
        outputBuildMaterialList: MutableList<BuildMaterialUiState>,
        buildMaterialList: List<BuildMaterialUiState>,
        initialItemAmount: Int,
        groupType: ItemGroupType? = null,
        itemList: List<ItemUiState> = emptyList(),
    ) {
        buildMaterialList.forEach { buildMaterial ->
            val buildMaterialItem = itemList.find { it.name == buildMaterial.name }
            buildMaterialItem?.let {
                if (buildMaterialItem.groupType.isValid(groupType = groupType)) {
                    addOrUpdateBuildMaterial(outputBuildMaterialList = outputBuildMaterialList, buildMaterial = buildMaterial, multiplier = initialItemAmount)
                } else {
                    outputBuildMaterialList.remove(buildMaterial)
                    processList(
                        outputBuildMaterialList = outputBuildMaterialList,
                        buildMaterialList = buildMaterialItem.buildMaterialListWrapper?.buildMaterialsList ?: emptyList(),
                        initialItemAmount = buildMaterial.amount * initialItemAmount,
                        groupType = groupType.toLowerGroupType(),
                        itemList = itemList,
                    )
                }
            }
        }
    }

    private fun ItemGroupType?.isValid(groupType: ItemGroupType?) = this == groupType
        || this?.toLowerGroupsList()?.contains(this) == true

    private fun addOrUpdateBuildMaterial(
        outputBuildMaterialList: MutableList<BuildMaterialUiState>,
        buildMaterial: BuildMaterialUiState,
        multiplier: Int,
    ) {
        val existingBuildMaterial = outputBuildMaterialList.find { it.name == buildMaterial.name }
        if (existingBuildMaterial != null) {
            outputBuildMaterialList.remove(existingBuildMaterial)
            outputBuildMaterialList.add(buildMaterial.copy(amount = existingBuildMaterial.amount + multiplier * buildMaterial.amount))
        } else {
            outputBuildMaterialList.add(buildMaterial.copy(amount = multiplier * buildMaterial.amount))
        }
    }

    companion object {

        data class InputData(
            val titleText: String?,
            val groupType: ItemGroupType?,
            val initialBuildMaterialsList: List<BuildMaterialUiState>,
            val initialItemAmount: Int,
            val itemList: List<ItemUiState>,
        )
    }
}
