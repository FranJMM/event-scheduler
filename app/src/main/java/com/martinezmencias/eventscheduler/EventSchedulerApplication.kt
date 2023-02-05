package com.martinezmencias.eventscheduler

import android.app.Application
import com.martinezmencias.eventscheduler.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class EventSchedulerApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@EventSchedulerApplication)
            modules(appModule)
        }
    }
}