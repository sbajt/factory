package com.sbajt.matscounter.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sbajt.matscounter.ui.models.ItemGroupType
import com.sbajt.matscounter.ui.models.screens.ItemListScreenUiState
import com.sbajt.matscounter.ui.useCases.ItemUiStateListUseCase
import com.sbajt.matscounter.ui.viewModels.ItemDetailsScreenViewModel.Companion.stateSubject
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ItemUiStateListViewModel : ViewModel(), KoinComponent {

    private val useCase: ItemUiStateListUseCase by inject()

    val uiState = useCase()
        .map { ItemListScreenUiState.Content(
            itemUiStateList = it.toPersistentList()
        ) }
        .catch { ItemListScreenUiState.Empty }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Companion.WhileSubscribed(5_000),
            initialValue = ItemListScreenUiState.Loading,
        )

    fun updateSelectedItem(
        selectedItemName: String?,
        selectedItemGroupType: ItemGroupType?,
        onFinish: (Boolean) -> Unit
    ) {
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
                onFinish.invoke(true)
            }
            onFinish.invoke(false)
        }
    }

    fun removeSelectedItem() {
        stateSubject.update { uiState ->
            uiState.copy(
                selectedItem = null,
                selectedItemAmount = 0,
            )
        }
    }
}
