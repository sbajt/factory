package com.sbajt.matscounter.ui.mappers

import com.sbajt.matscounter.ui.models.ItemGroupType
import com.sbajt.matscounter.ui.models.getName
import com.sbajt.matscounter.ui.models.screens.ItemBuildPathScreenUiState
import com.sbajt.matscounter.ui.models.toLowerGroupsList
import com.sbajt.matscounter.ui.models.views.BuildMaterialListWrapper
import com.sbajt.matscounter.ui.models.views.BuildMaterialUiState
import com.sbajt.matscounter.ui.models.views.ItemUiState
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ItemBuildPathScreenMapper: KoinComponent {

    private val matsWrapperMapper: BuildMaterialListWrapperMapper by inject()

    fun mapToUiState(inputData: InputData): ItemBuildPathScreenUiState = with(inputData) {
        ItemBuildPathScreenUiState.Content(
            selectedItem = selectedItem,
            selectedItemAmount = selectedItemAmount,
            selectedItemBuildMaterialListWrapperList = createBuildPathWrapperList(
                selectedItem = selectedItem,
                selectedItemAmount = selectedItemAmount,
                itemList = itemUiStateList,
            )
        )
    }

    private fun createBuildPathWrapperList(
        selectedItem: ItemUiState,
        selectedItemAmount: Int,
        itemList: List<ItemUiState>,
    ): List<BuildMaterialListWrapper> {
        val buildMaterialWrapperList = mutableListOf<BuildMaterialListWrapper>()

        selectedItem.groupType.toLowerGroupsList().forEach { groupType ->
            buildMaterialWrapperList.add(
                createBuildMaterialListWrapper(
                    groupType = groupType,
                    selectedItemAmount = selectedItemAmount,
                    initialBuildMaterialList = selectedItem.buildMaterialListWrapper?.buildMaterialsList
                        ?: emptyList(),
                )
            )
        }

        return createInitialBuildMaterialWrapperList(
            selectedItem = selectedItem,
            selectedItemAmount = selectedItemAmount,
        ) + buildMaterialWrapperList
    }

    private fun createInitialBuildMaterialWrapperList(
        selectedItem: ItemUiState,
        selectedItemAmount: Int,
    ): MutableList<BuildMaterialListWrapper> = if (selectedItem.buildMaterialListWrapper != null) {
        val selectedItemGroupType = selectedItem.groupType
        mutableListOf(
            selectedItem.buildMaterialListWrapper.copy(
                titleText = "${selectedItem.name} build materials",
                groupType = selectedItemGroupType,
                buildMaterialsList = selectedItem.buildMaterialListWrapper.buildMaterialsList.map {
                    it.copy(amount = it.amount * selectedItemAmount)
                }
            )
        )
    } else {
        mutableListOf()
    }

    private fun createBuildMaterialListWrapper(
        groupType: ItemGroupType,
        selectedItemAmount: Int,
        initialBuildMaterialList: List<BuildMaterialUiState>,
    ): BuildMaterialListWrapper {
        return BuildMaterialListWrapper(
            titleText = groupType.getName(),
            groupType = groupType,
            buildMaterialsList = initialBuildMaterialList.map { buildMaterial ->
                buildMaterial.copy(amount = buildMaterial.amount * selectedItemAmount)
            }
        )
    }


    private fun MutableList<BuildMaterialUiState>.updateBuildMaterial(
        buildMaterial: BuildMaterialUiState?,
        multiplier: Int,
    ) {
        if (buildMaterial != null) {
            val index = indexOf(buildMaterial)
            if (index != -1) {
                this[index] =
                    buildMaterial.copy(amount = buildMaterial.amount * multiplier)
            }
        }
    }

    private fun MutableList<BuildMaterialUiState>.splitBuildMaterial(
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

    private fun BuildMaterialUiState?.isValid(
        groupType: ItemGroupType?,
        itemList: List<ItemUiState>,
    ) = if (this != null) {
        val buildMaterialItem = itemList.find { it.name == name }
        buildMaterialItem?.groupType?.toLowerGroupsList()?.contains(groupType)
    } else {
        false
    }

    companion object {
        class InputData(
            val selectedItem: ItemUiState,
            val selectedItemAmount: Int,
            val itemUiStateList: List<ItemUiState>,
        )
    }
}
