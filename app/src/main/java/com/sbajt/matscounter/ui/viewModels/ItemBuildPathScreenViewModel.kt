package com.sbajt.matscounter.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sbajt.matscounter.ui.mappers.BuildPathScreenMapper
import com.sbajt.matscounter.ui.models.screens.BaseScreeUiState
import com.sbajt.matscounter.ui.stateSubject
import com.sbajt.matscounter.ui.useCases.ItemUiStateListUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ItemBuildPathScreenViewModel : ViewModel(), KoinComponent {
    private val mapper: BuildPathScreenMapper by inject()
    private val itemsUseCase: ItemUiStateListUseCase by inject()

    val uiState = combine(
        stateSubject,
        itemsUseCase()
    ) { state, itemList ->
        if (state.selectedItem == null) {
            BaseScreeUiState.Empty
        } else {
            mapper.mapToUiState(
                BuildPathScreenMapper.Companion.InputData(
                    selectedItem = state.selectedItem,
                    selectedItemAmount = state.selectedItemAmount,
                    itemList = itemList,
                )
            )
        }
    }
        .catch {
            BaseScreeUiState.Empty
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Companion.WhileSubscribed(5_000),
            initialValue = BaseScreeUiState.Loading,
        )
}
