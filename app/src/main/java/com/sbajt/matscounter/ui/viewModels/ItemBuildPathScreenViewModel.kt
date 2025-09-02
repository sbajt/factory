package com.sbajt.matscounter.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sbajt.matscounter.ui.mappers.ItemBuildPathScreenMapper
import com.sbajt.matscounter.ui.models.screens.ItemBuildPathScreenUiState
import com.sbajt.matscounter.ui.stateSubject
import com.sbajt.matscounter.ui.useCases.ItemUiStateListUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ItemBuildPathScreenViewModel : ViewModel(), KoinComponent {

    private val mapper: ItemBuildPathScreenMapper by inject()
    private val useCase: ItemUiStateListUseCase by inject()

    val uiState = combine(
        stateSubject,
        useCase()
    ) { state, itemUiStateList ->
        if (state.selectedItem == null) {
            throw IllegalStateException("Selected item is null")
        }
        mapper.mapToUiState(
            ItemBuildPathScreenMapper.Companion.InputData(
                selectedItem = state.selectedItem,
                selectedItemAmount = state.selectedItemAmount,
                itemUiStateList = itemUiStateList,
            )
        )
    }
        .catch { ItemBuildPathScreenUiState.Empty }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Companion.WhileSubscribed(5_000),
            initialValue = ItemBuildPathScreenUiState.Loading,
        )
}
