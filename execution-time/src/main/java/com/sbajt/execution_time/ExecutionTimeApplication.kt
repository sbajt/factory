package com.sbajt.execution_time

import android.app.Application
import com.sbajt.execution_time.di.executionTimeModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ExecutionTimeApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            printLogger()
            androidContext(this@ExecutionTimeApplication)
            modules(executionTimeModule)
        }
    }
}
