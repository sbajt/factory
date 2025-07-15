package com.sbajt.matscounter.ui.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sbajt.matscounter.domain.repositories.DataRepository
import com.sbajt.matscounter.ui.MainScreenViewModel
import com.sbajt.matscounter.ui.mappers.MainScreenMapper

class MainScreenViewModelFactory(
    private val dataRepository: DataRepository,
    private val mapper: MainScreenMapper,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainScreenViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainScreenViewModel(
                dataRepository = dataRepository,
                mapper = mapper,
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
