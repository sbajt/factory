package com.sbajt.matscounter.ui.di

import com.sbajt.matscounter.domain.di.domainModule
import com.sbajt.matscounter.ui.MainScreenViewModel
import com.sbajt.matscounter.ui.mappers.MainScreenMapper
import com.sbajt.matscounter.ui.useCases.ItemDomainListUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val uiModule = module {

    includes(domainModule)

    single {
        ItemDomainListUseCase(
            itemsRepository = get(),
        )
    }
    factoryOf(::MainScreenMapper)

    viewModel { MainScreenViewModel() }

}
