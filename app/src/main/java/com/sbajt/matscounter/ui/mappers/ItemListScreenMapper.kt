package com.sbajt.matscounter.ui.mappers

import com.sbajt.matscounter.ui.models.appBars.AppBarState
import com.sbajt.matscounter.ui.models.screens.ItemListScreenUiState
import com.sbajt.matscounter.ui.models.views.ItemUiState
import kotlinx.collections.immutable.toPersistentList
import org.koin.core.component.KoinComponent

class ItemListScreenMapper : KoinComponent {

    fun mapToUiState(inputData: InputData): ItemListScreenUiState = with(inputData) {
        ItemListScreenUiState(
            itemUiStateList = itemList.toPersistentList(),
            appBarState = AppBarState.ItemListAppBar(
                title = "Item list",
                actionList = emptyList()
            ),
        )
    }

    companion object {

        data class InputData(
            val itemList: List<ItemUiState>,
        )
    }
}
