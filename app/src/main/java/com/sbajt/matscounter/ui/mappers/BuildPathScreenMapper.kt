package com.sbajt.matscounter.ui.mappers

import com.sbajt.matscounter.ui.models.appBars.AppBarState
import com.sbajt.matscounter.ui.models.getName
import com.sbajt.matscounter.ui.models.screens.ItemBuildPathScreenUiState
import com.sbajt.matscounter.ui.models.toLowerGroupsList
import com.sbajt.matscounter.ui.models.views.BuildMaterialListWrapper
import com.sbajt.matscounter.ui.models.views.ItemUiState
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class BuildPathScreenMapper() : KoinComponent {

    private val matsWrapperMapper: BuildMaterialListWrapperMapper by inject()

    fun mapToUiState(inputData: InputData): ItemBuildPathScreenUiState = with(inputData) {
        ItemBuildPathScreenUiState(
            selectedItem = selectedItem,
            selectedItemAmount = selectedItemAmount,
            selectedItemBuildMaterialListWrapperList = createBuildPathWrapperList(
                selectedItem = selectedItem,
                selectedItemAmount = selectedItemAmount,
                itemList = itemList,
            ),
            appBarState = AppBarState.ItemBuildPathAppBar(
                title = selectedItem.name,
                actionList = emptyList()
            )
        )
    }

    private fun createBuildPathWrapperList(
        selectedItem: ItemUiState,
        selectedItemAmount: Int,
        itemList: List<ItemUiState>,
    ): List<BuildMaterialListWrapper> {
        val buildMaterialWrapperList = mutableListOf<BuildMaterialListWrapper>()
        selectedItem.buildMaterialListWrapper?.buildMaterialsList?.let {
            buildMaterialWrapperList.add(
                matsWrapperMapper.mapToUiState(
                    inputData = BuildMaterialListWrapperMapper.Companion.InputData(
                        titleText = "${selectedItem.name} build materials",
                        groupType = selectedItem.groupType,
                        initialBuildMaterialSet = selectedItem.buildMaterialListWrapper.buildMaterialsList.toHashSet(),
                        itemList = itemList,
                        initialItemAmount = selectedItemAmount,
                    )
                )
            )
        }

        selectedItem.groupType.toLowerGroupsList().forEach { groupType ->
            buildMaterialWrapperList.add(
                matsWrapperMapper.mapToUiState(
                    inputData = BuildMaterialListWrapperMapper.Companion.InputData(
                        titleText = groupType.getName(),
                        groupType = groupType,
                        initialBuildMaterialSet = selectedItem.buildMaterialListWrapper?.buildMaterialsList?.toHashSet()
                            ?: hashSetOf(),
                        itemList = itemList,
                        initialItemAmount = selectedItemAmount,
                    )
                )
            )
        }

        return buildMaterialWrapperList
    }

    companion object {

        data class InputData(
            val selectedItem: ItemUiState,
            val selectedItemAmount: Int,
            val itemList: List<ItemUiState>,
        )
    }
}
