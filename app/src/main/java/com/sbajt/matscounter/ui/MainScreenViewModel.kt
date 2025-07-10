package com.sbajt.matscounter.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sbajt.matscounter.ui.models.InputScreenUiState
import com.sbajt.matscounter.ui.models.MainUiState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.plus

class MainScreenViewModel : ViewModel() {

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e("${this.javaClass.name}", "Coroutine exception", throwable)
    }

    private val backgroundScope = viewModelScope + Dispatchers.IO + coroutineExceptionHandler

    private val stateSubject = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = stateSubject.asStateFlow()

    fun getMainUiState(): Flow<MainUiState> = flow {
        emit(
            MainUiState(
                inputScreenUiState = getInputScreenUiState(),
                itemGridUiState = emptyList()
            )
        )
    }

    private fun getInputScreenUiState() = InputScreenUiState()
}
