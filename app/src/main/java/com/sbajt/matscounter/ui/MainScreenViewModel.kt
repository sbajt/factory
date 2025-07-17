package com.sbajt.matscounter.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sbajt.matscounter.ui.mappers.MainScreenMapper
import com.sbajt.matscounter.ui.mappers.MainScreenMapper.Companion.InputData
import com.sbajt.matscounter.ui.models.DescriptionSectionUiState
import com.sbajt.matscounter.ui.models.InputSectionUiState
import com.sbajt.matscounter.ui.models.ItemGroupType
import com.sbajt.matscounter.ui.models.MainUiState
import com.sbajt.matscounter.ui.useCases.ItemDomainListUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.plus

class MainScreenViewModel(
    private val mapper: MainScreenMapper,
    useCase: ItemDomainListUseCase,
) : ViewModel() {

    private val backgroundScope = viewModelScope + Dispatchers.IO
    private val initialUiState = MainUiState(
        descriptionUiState = DescriptionSectionUiState(),
        inputSectionUiState = InputSectionUiState()
    )
    val stateSubject = MutableStateFlow(initialUiState)

    val uiState = combine(
        stateSubject,
        useCase(),
    ) { uiState, itemDomainList ->
        mapper.mapToUiState(
            InputData(
                selectedItem = uiState.descriptionUiState.selectedItem,
                selectedItemCount = uiState.inputSectionUiState.itemCount,
                itemDomainList = itemDomainList
            )
        )
    }
        .stateIn(
            scope = backgroundScope,
            started = WhileSubscribed(5_000),
            initialValue = initialUiState
        )

    fun updateSelectedItem(selectedItemName: String?, selectedItemGroupType: ItemGroupType?) {
        stateSubject.update { uiState ->
            val selectedItem = uiState
                .itemUiStateList
                .find { it.name == selectedItemName && it.groupType == selectedItemGroupType }
            val selectedItemCount: Int =
                if (selectedItem != uiState.inputSectionUiState.selectedItem) {
                    0
                } else {
                    uiState.inputSectionUiState.itemCount
                }
            uiState.copy(
                descriptionUiState = DescriptionSectionUiState(
                    selectedItem = selectedItem
                ),
                inputSectionUiState = InputSectionUiState(
                    itemCount = selectedItemCount
                ),
            )
        }
    }

    fun updateSelectedItemCount(newItemCount: Int) {
        stateSubject.update { uiState ->
            uiState.copy(
                inputSectionUiState = uiState.inputSectionUiState.copy(
                    itemCount = newItemCount
                )
            )
        }
    }
}
