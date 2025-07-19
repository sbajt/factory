package com.sbajt.matscounter.domain.di

import com.sbajt.matscounter.data.di.dataModule
import com.sbajt.matscounter.domain.repositories.ItemsRepository
import com.sbajt.matscounter.domain.repositories.ItemsRepositoryImpl
import org.koin.dsl.module

val domainModule = module {

    includes(dataModule)

    single<ItemsRepository> {
        ItemsRepositoryImpl(
            dataProvider = get(),
        )
    }
}
