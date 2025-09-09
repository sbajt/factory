package com.sbajt.matscounter.ui.mappers

import com.sbajt.matscounter.ui.models.ItemGroupType
import com.sbajt.matscounter.ui.models.views.BuildMaterialListWrapper
import com.sbajt.matscounter.ui.models.views.BuildMaterialUiState

class BuildMaterialListWrapperMapper {

    fun mapToUiState(inputData: InputData): BuildMaterialListWrapper {
        return BuildMaterialListWrapper(
            titleText = inputData.titleText,
            groupType = inputData.groupType,
            buildMaterialsList = inputData.buildMaterialsList,
        )
    }

    companion object {
        class InputData(
            val titleText: String?,
            val groupType: ItemGroupType?,
            val buildMaterialsList: List<BuildMaterialUiState>,
        )
    }
}
