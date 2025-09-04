package com.sbajt.matscounter.ui.mappers

import com.sbajt.matscounter.ui.models.ItemGroupType
import com.sbajt.matscounter.ui.models.screens.ItemBuildPathScreenUiState
import com.sbajt.matscounter.ui.models.views.BuildMaterialListWrapper
import com.sbajt.matscounter.ui.models.views.ItemUiState

class ItemBuildPathScreenMapper {

    fun mapToUiState(inputData: InputData): ItemBuildPathScreenUiState = with(inputData) {
        ItemBuildPathScreenUiState.Content(
            selectedItem = selectedItem,
            selectedItemAmount = selectedItemAmount,
            selectedItemBuildMaterialListWrapperList = createBuildPathList(
                selectedItem = selectedItem,
                selectedItemAmount = selectedItemAmount,
                itemList = itemUiStateList,
            )
        )
    }

    /**
     * Creates a list of [BuildMaterialListWrapper] representing the build path for a selected item.
     *
     * This function calculates the materials needed at each tier to build the [selectedItem].
     * It starts by checking if the [selectedItem] itself has a pre-defined build material list
     * where at least one material is of the same [ItemGroupType] as the [selectedItem].
     * If so, this list (with amounts adjusted by [selectedItemAmount]) becomes the first element
     * in the build path.
     *
     * Then, for each lower [ItemGroupType] (e.g., if [selectedItem] is TIER3, it will process TIER2, TIER1, BASIC_MATERIAL),
     * it calls [getBuildMaterialsListForGroup] to determine the materials required for that specific tier.
     * The results from [getBuildMaterialsListForGroup] are then added as a new [BuildMaterialListWrapper]
     * to the overall build path.
     *
     * @param selectedItem The [ItemUiState] for which the build path is being calculated.
     * @param selectedItemAmount The quantity of the [selectedItem] to be built.
     * @param itemList The complete list of available [ItemUiState]s, used to look up material details.
     * @return A list of [BuildMaterialListWrapper] where each wrapper represents a tier of materials
     *         needed to build the [selectedItem]. The list is ordered from the highest tier
     *         (or the item's direct build materials if applicable) down to the basic materials.
     */
    private fun createBuildPathList(
        selectedItem: ItemUiState,
        selectedItemAmount: Int,
        itemList: List<ItemUiState>,
    ): List<BuildMaterialListWrapper> {
        val buildMaterialWrapperList = createInitialBuildMaterialWrapperList(
            selectedItem = selectedItem,
            selectedItemAmount = selectedItemAmount,
            itemList = itemList,
        )

        selectedItem.groupType.toLowerGroupsList().forEachIndexed { index, groupType ->
//            processBuildMaterialGroup(
//                groupType = groupType,
//                selectedItem = selectedItem,
//                selectedItemAmount = selectedItemAmount,
//                itemList = itemList,
//                buildMaterialWrapperLisl = buildMaterialWrapperLisl,
//            )
            buildMaterialWrapperList.add(
                BuildMaterialListWrapper(
                    titleText = if (buildMaterialWrapperList.isEmpty()) {
                        "${groupType.getName()} materials"
                    } else {
                        "Item build materials"
                    },
                    groupType = groupType,
                    buildMaterialsList = emptyList()
                )
            )
        }
        return buildMaterialWrapperList.toList()
    }

    private fun createInitialBuildMaterialWrapperList(
        selectedItem: ItemUiState,
        selectedItemAmount: Int,
        itemList: List<ItemUiState>,
    ): MutableList<BuildMaterialListWrapper> = if (selectedItem.buildMaterialListWrapper != null
        && selectedItem.buildMaterialListWrapper.buildMaterialsList.any { buildMaterial ->
            itemList.find { it.name == buildMaterial.name }?.groupType == selectedItem.groupType
        }
    ) {
        mutableListOf(
            selectedItem.buildMaterialListWrapper.copy(
                titleText = "Item build materials",
                groupType = selectedItem.groupType,
                buildMaterialsList = selectedItem.buildMaterialListWrapper.buildMaterialsList.map {
                    it.copy(amount = it.amount * selectedItemAmount)
                }
            )
        )
    } else {
        mutableListOf()
    }

    companion object {

        class InputData(
            val selectedItem: ItemUiState,
            val selectedItemAmount: Int,
            val itemUiStateList: List<ItemUiState>,
        )
    }
}

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

fun getGroupTypeList(): List<ItemGroupType> = listOf(
    ItemGroupType.BASIC_MATERIAL,
    ItemGroupType.TIER1,
    ItemGroupType.TIER2,
    ItemGroupType.TIER3,
    ItemGroupType.TIER4
)
