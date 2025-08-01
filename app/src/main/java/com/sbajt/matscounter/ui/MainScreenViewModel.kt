package com.sbajt.matscounter.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sbajt.matscounter.ui.mappers.MainScreenMapper
import com.sbajt.matscounter.ui.mappers.MainScreenMapper.Companion.InputData
import com.sbajt.matscounter.ui.mappers.toItemUiState
import com.sbajt.matscounter.ui.models.ItemGroupType
import com.sbajt.matscounter.ui.models.MainScreenState
import com.sbajt.matscounter.ui.models.MainScreenUiState
import com.sbajt.matscounter.ui.useCases.ItemDomainListUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MainScreenViewModel : ViewModel(), KoinComponent {

    private val useCase: ItemDomainListUseCase by inject()
    private val mapper: MainScreenMapper by inject()

    private val stateSubject = MutableStateFlow(MainScreenState())
    private val itemDomainListShare = useCase()
        .shareIn(
            scope = viewModelScope,
            started = SharingStarted.Companion.Eagerly,
            replay = 1,
        )

    val uiState = combine(
        stateSubject,
        itemDomainListShare,
    ) { state, itemDomainList ->
        mapper.mapToUiState(
            InputData(
                selectedItem = state.selectedItem,
                selectedItemCount = state.itemCount,
                itemDomainList = itemDomainList
            )
        )
    }
        .catch { MainScreenUiState.Empty }
        .stateIn(
            scope = viewModelScope,
            started = WhileSubscribed(5_000),
            initialValue = MainScreenUiState.Loading,
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
                    val selectedItem = list
                        .find { it.name == selectedItemName && it.groupType == selectedItemGroupType?.ordinal }
                        ?.toItemUiState()
                    uiState.copy(
                        selectedItem = selectedItem,
                        itemCount = 1,
                    )
                }
                onFinish.invoke(true)
            }
            onFinish.invoke(false)
        }

    }

    fun updateSelectedItemCount(newItemCount: Int) {
        stateSubject.update { uiState ->
            uiState.copy(itemCount = newItemCount)
        }
    }

    fun removeSelectedItem() {
        stateSubject.update { uiState ->
            uiState.copy(
                selectedItem = null,
                itemCount = 0,
            )
        }
    }
}
