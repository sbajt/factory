package com.sbajt.matscounter.ui.mappers

import com.sbajt.matscounter.ui.models.ItemGroupType
import com.sbajt.matscounter.ui.models.toLowerGroupType
import com.sbajt.matscounter.ui.models.toLowerGroupsList
import com.sbajt.matscounter.ui.models.views.BuildMaterialListWrapper
import com.sbajt.matscounter.ui.models.views.BuildMaterialUiState
import com.sbajt.matscounter.ui.models.views.ItemUiState

class BuildMaterialListWrapperMapper {

    fun mapToUiState(inputData: InputData): BuildMaterialListWrapper {

        val outputBuildMaterialSet = hashSetOf<BuildMaterialUiState>()
        processList(
            outputBuildMaterialSet = outputBuildMaterialSet,
            buildMaterialList = inputData.initialBuildMaterialSet,
            multiplier = inputData.initialItemAmount,
            groupType = inputData.groupType,
            itemList = inputData.itemList,
        )
        return BuildMaterialListWrapper(
            titleText = inputData.titleText,
            groupType = inputData.groupType,
            buildMaterialsList = outputBuildMaterialSet.sortedBy { it.name },
        )
    }

    private fun processList(
        outputBuildMaterialSet: HashSet<BuildMaterialUiState>,
        buildMaterialList: HashSet<BuildMaterialUiState>,
        multiplier: Int,
        groupType: ItemGroupType?,
        itemList: List<ItemUiState> = emptyList(),
    ) {
        buildMaterialList.forEach { buildMaterial ->
            val buildMaterialItem = itemList.find { it.name == buildMaterial.name }
            buildMaterialItem?.let {
                if (isValid(buildMaterialGroupType = buildMaterialItem.groupType, targetGroupType = groupType)) {
                    addOrUpdateBuildMaterial(outputBuildMaterialSet = outputBuildMaterialSet, buildMaterial = buildMaterial, multiplier = multiplier)
                } else {
                    outputBuildMaterialSet.remove(buildMaterial)
                    processList(
                        outputBuildMaterialSet = outputBuildMaterialSet,
                        buildMaterialList = buildMaterialItem.buildMaterialListWrapper?.buildMaterialsList?.toHashSet() ?: hashSetOf(),
                        multiplier = buildMaterial.amount * multiplier,
                        groupType = groupType.toLowerGroupType(),
                        itemList = itemList,
                    )
                }
            }
        }
    }

    private fun isValid(buildMaterialGroupType: ItemGroupType, targetGroupType: ItemGroupType?) = buildMaterialGroupType == ItemGroupType.BASIC_MATERIAL
        || buildMaterialGroupType == targetGroupType
        || targetGroupType.toLowerGroupsList().contains(buildMaterialGroupType)

    private fun addOrUpdateBuildMaterial(
        outputBuildMaterialSet: HashSet<BuildMaterialUiState>,
        buildMaterial: BuildMaterialUiState,
        multiplier: Int,
    ) {
        val existingBuildMaterial = outputBuildMaterialSet.find { it.name == buildMaterial.name }
        if (existingBuildMaterial != null) {
            outputBuildMaterialSet.remove(existingBuildMaterial)
            outputBuildMaterialSet.add(buildMaterial.copy(amount = existingBuildMaterial.amount + multiplier * buildMaterial.amount))
        } else {
            outputBuildMaterialSet.add(buildMaterial.copy(amount = multiplier * buildMaterial.amount))
        }
    }

    companion object {

        data class InputData(
            val titleText: String?,
            val groupType: ItemGroupType?,
            val initialBuildMaterialSet: HashSet<BuildMaterialUiState>,
            val initialItemAmount: Int,
            val itemList: List<ItemUiState>,
        )
    }
}
