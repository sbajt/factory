package com.sbajt.matscounter.ui.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.sbajt.matscounter.ui.mappers.ItemDetailsScreenMapper
import com.sbajt.matscounter.ui.models.screens.BaseScreeUiState
import com.sbajt.matscounter.ui.navigation.ItemBuildPath
import com.sbajt.matscounter.ui.stateSubject
import com.sbajt.matscounter.ui.useCases.ItemUiStateListUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ItemDetailsScreenViewModel : ViewModel(), KoinComponent {

    private val mapper: ItemDetailsScreenMapper by inject()
    private val itemsUseCase: ItemUiStateListUseCase by inject()

    val uiState = combine(
        stateSubject,
        itemsUseCase(),
    ) { state, itemList ->
        if (state.selectedItem == null) {
            BaseScreeUiState.Empty
        } else {
            Log.d("ItemDetailsScreenViewModel", "Using ItemDetailsScreenMapper...")
            mapper.mapToUiState(
                ItemDetailsScreenMapper.Companion.InputData(
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

    fun updateSelectedItemAmount(newItemCount: Int) {
        stateSubject.update { uiState ->
            uiState.copy(selectedItemAmount = newItemCount)
        }
    }

    fun navigateToBuildPath(navController: NavHostController) {
        navController.navigate(ItemBuildPath)
    }
}
