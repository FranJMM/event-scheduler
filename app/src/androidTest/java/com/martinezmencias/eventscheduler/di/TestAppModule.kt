package com.martinezmencias.eventscheduler.di

import com.martinezmencias.eventscheduler.BuildConfig
import com.martinezmencias.eventscheduler.data.database.EventDatabase
import com.martinezmencias.eventscheduler.data.server.RemoteConnection
import com.martinezmencias.eventscheduler.data.server.RemoteService
import org.koin.dsl.module

val testAppModule = module {

    single<String>(NAMED_ENDPOINT) { "http://localhost:8080" }

    single<EventDatabase> { EventDatabase.createDatabaseForTests(get()) }
}