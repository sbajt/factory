package com.sbajt.matscounter.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sbajt.matscounter.ui.mappers.MainScreenMapper
import com.sbajt.matscounter.ui.mappers.MainScreenMapper.Companion.InputData
import com.sbajt.matscounter.ui.mappers.toUiState
import com.sbajt.matscounter.ui.models.ItemGroupType
import com.sbajt.matscounter.ui.models.MainScreenState
import com.sbajt.matscounter.ui.models.MainScreenUiState
import com.sbajt.matscounter.ui.useCases.ItemDomainListUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus

class MainScreenViewModel(
    private val mapper: MainScreenMapper,
    private val useCase: ItemDomainListUseCase,
) : ViewModel() {

    private val backgroundScope = viewModelScope + Dispatchers.IO
    private val stateSubject = MutableStateFlow(MainScreenState())
    private val itemDomainList = useCase().shareIn(
        scope = backgroundScope,
        started = WhileSubscribed(30_000),
        replay = 1,
    )

    val uiState = combine(
        stateSubject,
        itemDomainList,
    ) { mainScreenState, itemDomainList ->
        mapper.mapToUiState(
            InputData(
                selectedItem = mainScreenState.selectedItem,
                selectedItemCount = mainScreenState.itemCount,
                itemDomainList = itemDomainList
            )
        )
    }
        .catch { MainScreenUiState.Empty }
        .stateIn(
            scope = backgroundScope,
            started = WhileSubscribed(30_000),
            initialValue = MainScreenUiState.Loading
        )

    fun updateSelectedItem(selectedItemName: String?, selectedItemGroupType: ItemGroupType?) {
        viewModelScope.launch {
            stateSubject.update { uiState ->
                val list = useCase().firstOrNull() ?: emptyList()
                val selectedItem = list.firstOrNull { it.name == selectedItemName && it.groupType == selectedItemGroupType?.ordinal }?.toUiState()
                val selectedItemCount: Int = if (selectedItem != uiState.selectedItem) {
                    0
                } else {
                    uiState.itemCount
                }
                uiState.copy(
                    selectedItem = selectedItem,
                    itemCount = selectedItemCount,
                )
            }
        }
    }

    fun updateSelectedItemCount(newItemCount: Int) {
        stateSubject.update { uiState ->
            uiState.copy(itemCount = newItemCount)
        }
    }
}
