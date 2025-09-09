package com.sbajt.matscounter.ui.mappers

import com.sbajt.matscounter.ui.models.ItemGroupType
import com.sbajt.matscounter.ui.models.isGreater
import com.sbajt.matscounter.ui.models.views.BuildMaterialListWrapper
import com.sbajt.matscounter.ui.models.views.BuildMaterialUiState
import com.sbajt.matscounter.ui.models.views.ItemUiState

class BuildMaterialListWrapperMapper {

    fun mapToUiState(inputData: InputData): BuildMaterialListWrapper {
        val buildMaterialList = mutableListOf<BuildMaterialUiState>()

//        inputData.initialBuildMaterialsList.filter { buildMaterial ->
//            val itemGroupType = inputData.itemList.find { it.name == buildMaterial.name }?.groupType
//            itemGroupType?.isGreater(inputData.groupType) == false
//        }
//            .forEach { buildMaterial ->
//                val buildMaterialItem = inputData.itemList.find { it.name == buildMaterial.name }
//                if (isBuildMaterialValid(groupType = inputData.groupType, buildMaterialItem = buildMaterialItem)) {
//                    if (buildMaterialItem?.buildMaterialListWrapper != null &&
//                        buildMaterialItem.buildMaterialListWrapper.buildMaterialsList.isNotEmpty()
//                    ) {
//                        buildMaterialList.updateBuildMaterial(
//                            buildMaterial = buildMaterial,
//                            multiplier = buildMaterial.amount
//                        )
//                    }
//                } else {
//                    buildMaterialList.splitItemOnBuildComponents(
//                        buildMaterial = buildMaterial,
//                        buildMaterialItem = buildMaterialItem,
//                        multiplier = buildMaterial.amount
//                    )
//                }
//            }
        inputData.initialBuildMaterialsList.map {
            buildMaterialList.updateBuildMaterial(it, multiplier = inputData.initialItemAmount)
        }

        return BuildMaterialListWrapper(
            titleText = inputData.titleText,
            groupType = inputData.groupType,
            buildMaterialsList = buildMaterialList,
        )
    }

    private fun MutableList<BuildMaterialUiState>.updateBuildMaterial(
        buildMaterial: BuildMaterialUiState?,
        multiplier: Int,
    ) {
        if (buildMaterial != null) {
            val existingMaterial = this.find { it.name == buildMaterial.name }
            if (existingMaterial != null) {
                val index = indexOf(existingMaterial)
                if (index != -1) {
                    this[index] = existingMaterial.copy(
                        amount = buildMaterial.amount * multiplier + existingMaterial.amount
                    )
                } else {
                    this.add(buildMaterial.copy(amount = buildMaterial.amount * multiplier))
                }
            } else {
                this.add(buildMaterial.copy(amount = buildMaterial.amount * multiplier))
            }
        }
    }

    private fun MutableList<BuildMaterialUiState>.splitItemOnBuildComponents(
        buildMaterial: BuildMaterialUiState,
        buildMaterialItem: ItemUiState?,
        multiplier: Int,
    ) {
        if (buildMaterialItem != null) {
            this.remove(buildMaterial)
            buildMaterialItem.buildMaterialListWrapper?.buildMaterialsList?.forEach { newBuildMaterial ->
                val existingMaterial = this.find { it.name == newBuildMaterial.name }
                if (existingMaterial != null) {
                    val index = indexOf(existingMaterial)
                    if (index != -1) {
                        this[index] = existingMaterial.copy(
                            amount = newBuildMaterial.amount * multiplier + existingMaterial.amount
                        )
                    } else {
                        this.add(newBuildMaterial.copy(amount = newBuildMaterial.amount * multiplier))
                    }
                }
            }
        }
    }

    private fun isBuildMaterialValid(groupType: ItemGroupType?, buildMaterialItem: ItemUiState?): Boolean =
        buildMaterialItem?.groupType?.isGreater(groupType) != true

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
