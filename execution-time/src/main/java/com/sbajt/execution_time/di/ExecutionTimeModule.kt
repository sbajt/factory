package com.sbajt.execution_time.di

import com.sbajt.execution_time.storage.DataManager
import com.sbajt.execution_time.storage.FileDataManagerImpl
import com.sbajt.execution_time.time.TimeMeasureManager
import com.sbajt.execution_time.time.TimeMeasureManagerImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val executionTimeModule = module {

    single<TimeMeasureManager> {
        TimeMeasureManagerImpl(
            dataManager = get()
        )
    }

    single<DataManager> {
        FileDataManagerImpl(
            context = androidContext()
        )
    }
}
