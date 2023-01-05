package com.martinezmencias.eventscheduler.di

import com.martinezmencias.eventscheduler.BuildConfig
import com.martinezmencias.eventscheduler.data.datasource.EventLocalDataSource
import com.martinezmencias.eventscheduler.data.datasource.EventRemoteDataSource
import com.martinezmencias.eventscheduler.data.repository.EventRepository
import com.martinezmencias.eventscheduler.data.database.EventRoomDataSource
import com.martinezmencias.eventscheduler.data.server.EventServerDataSource
import com.martinezmencias.eventscheduler.data.server.RemoteConnection
import com.martinezmencias.eventscheduler.data.server.RemoteService
import com.martinezmencias.eventscheduler.ui.list.ListViewModel
import com.martinezmencias.eventscheduler.usecases.RequestEventsUseCase
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appModules = module {

    // ViewModel
    viewModelOf(::ListViewModel)

    // UseCase
    singleOf(::RequestEventsUseCase)

    // Repository
    singleOf(::EventRepository)

    // DataSource
    singleOf(::EventServerDataSource) { bind<EventRemoteDataSource>() }
    singleOf(::EventRoomDataSource) { bind<EventLocalDataSource>() }

    // Connection
    single<RemoteService> { RemoteConnection.provideRemoteService(BuildConfig.ENDPOINT, get()) }
    single<OkHttpClient> { RemoteConnection.provideOkHttpClient() }
}