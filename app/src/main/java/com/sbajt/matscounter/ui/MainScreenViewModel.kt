package com.sbajt.matscounter.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.sbajt.matscounter.data.models.ItemGroupType
import com.sbajt.matscounter.domain.repositories.DataRepository
import com.sbajt.matscounter.ui.mappers.MainScreenMapper
import com.sbajt.matscounter.ui.mappers.MainScreenMapper.Companion.InputData
import com.sbajt.matscounter.ui.mappers.toUiState
import com.sbajt.matscounter.ui.models.DescriptionSectionUiState
import com.sbajt.matscounter.ui.models.InputSectionUiState
import com.sbajt.matscounter.ui.models.MainUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import kotlinx.coroutines.selects.select

internal class MainScreenViewModel(application: Application) : AndroidViewModel(application) {

    private val backgroundScope = viewModelScope + Dispatchers.IO

    private val stateSubject = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = stateSubject.asStateFlow()

    private val dataRepository = DataRepository(assetManager = application.assets)
    private val mapper = MainScreenMapper()

    fun fetchData() {
        backgroundScope.launch {
            val mainUiState = mapper.mapToUiState(
                InputData(
                    selectedItem = stateSubject.value.descriptionUiState.selectedItem,
                    selectedItemCount = stateSubject.value.inputSectionUiState.itemCount,
                    itemDomainList = dataRepository()
                )
            )
            stateSubject.update { uiState -> mainUiState }
        }
    }

    fun updateSelectedItem(selectedItemName: String?, selectedItemGroupType: ItemGroupType?) {
        stateSubject.update { uiState ->
            val selectedItem = dataRepository.getItemByNameAndType(selectedItemName, selectedItemGroupType)?.toUiState()
            val selectedItemCount = if (selectedItem != uiState.inputSectionUiState.selectedItem) {
                0
            } else {
                uiState.inputSectionUiState.itemCount
            }
            uiState.copy(
                descriptionUiState = DescriptionSectionUiState(
                    selectedItem = selectedItem
                ),
                inputSectionUiState = InputSectionUiState(
                    selectedItem = selectedItem,
                    itemCount = selectedItemCount
                ),
            )
        }
    }
}
