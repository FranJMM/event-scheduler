package com.martinezmencias.eventscheduler.di

import com.martinezmencias.eventscheduler.data.server.EventRemoteDataSource
import com.martinezmencias.eventscheduler.data.server.EventServerDataSource
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appModules = module {
    singleOf(::EventServerDataSource){ bind<EventRemoteDataSource>() }
}