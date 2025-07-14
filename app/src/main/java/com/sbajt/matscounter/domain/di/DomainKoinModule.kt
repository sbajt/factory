package com.sbajt.matscounter.domain.di

import com.sbajt.matscounter.domain.DataRepository
import com.sbajt.matscounter.domain.repositories.DataRepositoryImpl
import org.koin.dsl.module

val domainModule = module {

    single<DataRepository> {
        DataRepositoryImpl(
            dataProvider = get(),
        )
    }
}