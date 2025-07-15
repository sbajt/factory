package com.sbajt.matscounter.ui.di

import com.sbajt.matscounter.data.di.dataModule
import com.sbajt.matscounter.domain.di.domainModule
import com.sbajt.matscounter.ui.MainScreenViewModel
import com.sbajt.matscounter.ui.mappers.MainScreenMapper
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import com.sbajt.matscounter.domain.repositories.DataRepository

val uiModule = dataModule + domainModule + module {

    viewModel{ _ ->
        MainScreenViewModel(
            dataRepository = get(),
            mapper = get()
        )
    }
    factoryOf(::MainScreenMapper)

}
