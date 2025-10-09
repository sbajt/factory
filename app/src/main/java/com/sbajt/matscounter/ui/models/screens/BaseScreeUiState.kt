package com.sbajt.matscounter.ui.models.screens

sealed interface BaseScreeUiState {
    object Loading : BaseScreeUiState

    object Empty : BaseScreeUiState

}