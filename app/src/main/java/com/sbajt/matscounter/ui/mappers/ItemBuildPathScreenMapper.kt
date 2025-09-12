package com.sbajt.matscounter.ui.mappers

import com.sbajt.matscounter.ui.models.getName
import com.sbajt.matscounter.ui.models.screens.ItemBuildPathScreenUiState
import com.sbajt.matscounter.ui.models.toLowerGroupsList
import com.sbajt.matscounter.ui.models.views.BuildMaterialListWrapper
import com.sbajt.matscounter.ui.models.views.ItemUiState
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ItemBuildPathScreenMapper() : KoinComponent {

    private val matsWrapperMapper: BuildMaterialListWrapperMapper by inject()

    fun mapToUiState(inputData: InputData): ItemBuildPathScreenUiState = with(inputData) {
        ItemBuildPathScreenUiState.Content(
            selectedItem = selectedItem,
            selectedItemAmount = selectedItemAmount,
            selectedItemBuildMaterialListWrapperList = createBuildPathWrapperList(
                selectedItem = selectedItem,
                selectedItemAmount = selectedItemAmount,
                itemList = itemList,
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
                matsWrapperMapper.mapToUiState(
                    inputData = BuildMaterialListWrapperMapper.Companion.InputData(
                        titleText = groupType.getName(),
                        groupType = groupType,
                        initialBuildMaterialsList = selectedItem.buildMaterialListWrapper?.buildMaterialsList ?: listOf(),
                        itemList = itemList,
                        initialItemAmount = selectedItemAmount,
                    )
                )
            )
        }

        return createInitialBuildMaterialWrapperList(
            selectedItem = selectedItem,
            selectedItemAmount = selectedItemAmount,
            itemList = itemList,
        ) + buildMaterialWrapperList
    }

    private fun createInitialBuildMaterialWrapperList(
        selectedItem: ItemUiState,
        selectedItemAmount: Int,
        itemList: List<ItemUiState>,
    ): MutableList<BuildMaterialListWrapper> = if (selectedItem.buildMaterialListWrapper != null) {
        mutableListOf(
            matsWrapperMapper.mapToUiState(
                inputData = BuildMaterialListWrapperMapper.Companion.InputData(
                    titleText = "${selectedItem.name} build materials",
                    groupType =  selectedItem.groupType,
                    initialBuildMaterialsList = selectedItem.buildMaterialListWrapper.buildMaterialsList.map {
                        it.copy(amount = it.amount * selectedItemAmount)
                    },
                    itemList = itemList,
                    initialItemAmount = selectedItemAmount,
                    hasOnlyInitialBuildMaterials = true
                )
            )
        )
    } else {
        mutableListOf()
    }

    companion object {

        data class InputData(
            val selectedItem: ItemUiState,
            val selectedItemAmount: Int,
            val itemList: List<ItemUiState>,
        )
    }
}
