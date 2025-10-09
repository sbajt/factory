package com.sbajt.matscounter.ui.di

import com.sbajt.matscounter.domain.di.domainModule
import com.sbajt.matscounter.ui.mappers.BuildMaterialListWrapperMapper
import com.sbajt.matscounter.ui.mappers.BuildPathScreenMapper
import com.sbajt.matscounter.ui.mappers.ItemDetailsScreenMapper
import com.sbajt.matscounter.ui.mappers.ItemListScreenMapper
import com.sbajt.matscounter.ui.useCases.ItemUiStateListUseCase
import com.sbajt.matscounter.ui.viewModels.ItemBuildPathScreenViewModel
import com.sbajt.matscounter.ui.viewModels.ItemDetailsScreenViewModel
import com.sbajt.matscounter.ui.viewModels.ItemListScreenViewModel
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

    factoryOf(::ItemListScreenMapper)
    factoryOf(::ItemDetailsScreenMapper)
    factoryOf(::BuildPathScreenMapper)
    factoryOf(::BuildMaterialListWrapperMapper)

    viewModel { ItemListScreenViewModel() }
    viewModel { ItemDetailsScreenViewModel() }
    viewModel { ItemBuildPathScreenViewModel() }

}
