package com.sbajt.matscounter.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sbajt.matscounter.ui.models.ItemGroupType
import com.sbajt.matscounter.ui.models.appBars.AppBarState
import com.sbajt.matscounter.ui.models.screens.ItemListScreenUiState
import com.sbajt.matscounter.ui.stateSubject
import com.sbajt.matscounter.ui.useCases.ItemUiStateListUseCase
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ItemListViewModel : ViewModel(), KoinComponent {

    private val itemsUseCase: ItemUiStateListUseCase by inject()

    val uiState = combine(
        itemsUseCase(),
        stateSubject,
    ) { list, state ->
        delay(1_000)
        if (list.isEmpty()) {
            ItemListScreenUiState.Empty
        } else {
            ItemListScreenUiState.Content(
                itemUiStateList = list.toPersistentList(),
                appBarState = state.appBarState as? AppBarState.ItemList,
            )
        }
    }
        .stateIn(
            viewModelScope,
            WhileSubscribed(5_000),
            ItemListScreenUiState.Loading
        )

    fun updateSelectedItem(
        selectedItemName: String?,
        selectedItemGroupType: ItemGroupType?,
    ) {
        if (selectedItemGroupType != ItemGroupType.BASIC_MATERIAL) {
            val previousSelectedItem = stateSubject.value.selectedItem
            if (selectedItemName != previousSelectedItem?.name) {
                viewModelScope.launch {
                    val list = itemsUseCase().firstOrNull() ?: emptyList()
                    val selectedItem =
                        list.find { it.name == selectedItemName && it.groupType == selectedItemGroupType }
                    stateSubject.update { uiState ->
                        uiState.copy(
                            selectedItem = selectedItem,
                            selectedItemAmount = 1,
                        )
                    }
                }
            }
        }
    }
}
