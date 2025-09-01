package com.sbajt.matscounter.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sbajt.matscounter.ui.mappers.ItemDetailsScreenMapper
import com.sbajt.matscounter.ui.models.AppState
import com.sbajt.matscounter.ui.models.screens.ItemDetailsScreenUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ItemDetailsScreenViewModel : ViewModel(), KoinComponent {

    private val mapper: ItemDetailsScreenMapper by inject()

    val uiState = ItemUiStateListViewModel.stateSubject
        .map { state ->
            mapper.mapToUiState(
                ItemDetailsScreenMapper.Companion.InputData(
                    selectedItem = state.selectedItem,
                    selectedItemAmount = state.selectedItemAmount,
                )
            )
        }
        .catch { ItemDetailsScreenUiState.Empty }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Companion.WhileSubscribed(5_000),
            initialValue = ItemDetailsScreenUiState.Loading,
        )

    fun updateSelectedItemAmount(newItemCount: Int) {
        ItemUiStateListViewModel.stateSubject.update { uiState ->
            uiState.copy(selectedItemAmount = newItemCount)
        }
    }
}
