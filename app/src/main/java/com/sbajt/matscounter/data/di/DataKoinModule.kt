package com.sbajt.matscounter.data.di

import com.sbajt.matscounter.data.DataProvider
import com.sbajt.matscounter.data.DataProviderImpl
import org.koin.dsl.module

val dataModule = module {

    single<DataProvider> {
        DataProviderImpl(
            context = get(),
        )
    }
}
