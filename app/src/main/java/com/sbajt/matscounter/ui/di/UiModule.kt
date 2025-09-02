package com.sbajt.matscounter.ui.di

import com.sbajt.matscounter.domain.di.domainModule
import com.sbajt.matscounter.ui.mappers.ItemBuildPathScreenMapper
import com.sbajt.matscounter.ui.mappers.ItemDetailsScreenMapper
import com.sbajt.matscounter.ui.useCases.ItemUiStateListUseCase
import com.sbajt.matscounter.ui.viewModels.ItemBuildPathScreenViewModel
import com.sbajt.matscounter.ui.viewModels.ItemDetailsScreenViewModel
import com.sbajt.matscounter.ui.viewModels.ItemListViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val uiModule = module {

    includes(domainModule)

    single {
        ItemUiStateListUseCase(
            itemsRepository = get(),
        )
    }

    factoryOf(::ItemDetailsScreenMapper)
    factoryOf(::ItemBuildPathScreenMapper)

    viewModel { ItemListViewModel() }
    viewModel { ItemDetailsScreenViewModel() }
    viewModel { ItemBuildPathScreenViewModel() }

}
