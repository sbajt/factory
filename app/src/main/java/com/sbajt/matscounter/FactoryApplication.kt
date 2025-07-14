package com.sbajt.matscounter

import android.app.Application
import com.sbajt.matscounter.data.di.dataModule
import com.sbajt.matscounter.domain.di.domainModule
import com.sbajt.matscounter.ui.di.uiModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class FactoryApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidLogger()
            androidContext(this@FactoryApplication)
            modules(
                dataModule,
                domainModule,
                uiModule,
            )
        }
    }

}