package com.martinezmencias.eventscheduler.data.repository

import com.martinezmencias.eventscheduler.data.datasource.EventLocalDataSource
import com.martinezmencias.eventscheduler.data.datasource.EventRemoteDataSource
import com.martinezmencias.eventscheduler.domain.Event

class EventRepository(
    private val localDataSource: EventLocalDataSource,
    private val remoteDataSource: EventRemoteDataSource
) {

    val events get() = localDataSource.events

    suspend fun requestEvents(): List<Event> {
        return remoteDataSource.requestEvents()
    }
}