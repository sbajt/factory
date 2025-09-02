package com.sbajt.matscounter.ui.viewModels

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.sbajt.matscounter.ui.models.ItemGroupType
import com.sbajt.matscounter.ui.models.screens.ItemListScreenUiState
import com.sbajt.matscounter.ui.navigation.ItemDetails
import com.sbajt.matscounter.ui.stateSubject
import com.sbajt.matscounter.ui.useCases.ItemUiStateListUseCase
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.delayEach
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ItemListViewModel : ViewModel(), KoinComponent {

    private val useCase: ItemUiStateListUseCase by inject()

    val uiState = useCase()
        .map {
            delay(1_000)
            if (it.isEmpty()) {
                ItemListScreenUiState.Empty
            } else {
                ItemListScreenUiState.Content(itemUiStateList = it.toPersistentList())
            }
        }
        .stateIn(viewModelScope, WhileSubscribed(5_000), ItemListScreenUiState.Loading)

    fun updateSelectedItem(
        selectedItemName: String?,
        selectedItemGroupType: ItemGroupType?,
        navController: NavHostController?,
    ) {
        if (selectedItemGroupType != ItemGroupType.BASIC_MATERIAL) {
            viewModelScope.launch {
                val previousSelectedItem = stateSubject.value.selectedItem
                if (selectedItemName != previousSelectedItem?.name) {
                    stateSubject.update { uiState ->
                        val list = useCase().firstOrNull() ?: emptyList()
                        val selectedItem = list.find { it.name == selectedItemName && it.groupType == selectedItemGroupType }
                        uiState.copy(
                            selectedItem = selectedItem,
                            selectedItemAmount = 1,
                        )
                    }
                    navController?.navigate(ItemDetails)
                }
            }
        }
    }
}
