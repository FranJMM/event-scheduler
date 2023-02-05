package com.martinezmencias.eventscheduler.di

import com.martinezmencias.eventscheduler.BuildConfig
import com.martinezmencias.eventscheduler.data.PermissionChecker
import com.martinezmencias.eventscheduler.data.database.EventDao
import com.martinezmencias.eventscheduler.data.database.EventDatabase
import com.martinezmencias.eventscheduler.data.datasource.EventLocalDataSource
import com.martinezmencias.eventscheduler.data.datasource.EventRemoteDataSource
import com.martinezmencias.eventscheduler.data.repository.EventRepository
import com.martinezmencias.eventscheduler.data.repository.RegionRepository
import com.martinezmencias.eventscheduler.data.database.EventRoomDataSource
import com.martinezmencias.eventscheduler.data.database.VenueDao
import com.martinezmencias.eventscheduler.data.datasource.LocationDataSource
import com.martinezmencias.eventscheduler.data.location.PlayServicesLocationDataSource
import com.martinezmencias.eventscheduler.data.permissions.AndroidPermissionChecker
import com.martinezmencias.eventscheduler.data.server.EventServerDataSource
import com.martinezmencias.eventscheduler.data.server.RemoteConnection
import com.martinezmencias.eventscheduler.data.server.RemoteService
import com.martinezmencias.eventscheduler.ui.detail.DetailViewModel
import com.martinezmencias.eventscheduler.ui.list.ListViewModel
import com.martinezmencias.eventscheduler.usecases.FindEventUseCase
import com.martinezmencias.eventscheduler.usecases.GetEventsUseCase
import com.martinezmencias.eventscheduler.usecases.RequestEventsUseCase
import com.martinezmencias.eventscheduler.usecases.SwitchEventFavoriteUseCase
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appModule = module {

    // ViewModel
    viewModelOf(::ListViewModel)
    viewModel { (eventId: String) -> DetailViewModel(eventId = eventId, get(), get()) }

    // UseCase
    singleOf(::RequestEventsUseCase)
    singleOf(::GetEventsUseCase)
    singleOf(::FindEventUseCase)
    singleOf(::SwitchEventFavoriteUseCase)

    // Repository
    singleOf(::EventRepository)
    singleOf(::RegionRepository)

    // DataSource
    singleOf(::EventServerDataSource) { bind<EventRemoteDataSource>() }
    singleOf(::EventRoomDataSource) { bind<EventLocalDataSource>() }

    // Connection
    single<RemoteService> { RemoteConnection.createRemoteService(BuildConfig.ENDPOINT, get()) }
    single<OkHttpClient> { RemoteConnection.createOkHttpClient() }

    // Database
    single<EventDatabase> { EventDatabase.createDatabase(get()) }
    single<EventDao> { get<EventDatabase>().eventDao() }
    single<VenueDao> { get<EventDatabase>().venueDao() }

    // Permissions
    singleOf(::AndroidPermissionChecker) { bind<PermissionChecker>() }

    // Location
    singleOf(::PlayServicesLocationDataSource) { bind<LocationDataSource>() }
}