package com.martinezmencias.eventscheduler.di

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TestEventSchedulerApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@TestEventSchedulerApplication)
            modules(appModule)
        }
    }
}