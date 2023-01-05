package com.martinezmencias.eventscheduler.data.repository

import com.martinezmencias.eventscheduler.data.datasource.EventLocalDataSource
import com.martinezmencias.eventscheduler.data.datasource.EventRemoteDataSource
import com.martinezmencias.eventscheduler.domain.Event

class EventRepository(
    private val localDataSource: EventLocalDataSource,
    private val remoteDataSource: EventRemoteDataSource
) {

    val events = localDataSource.events

    suspend fun requestEvents() {
        val events = remoteDataSource.requestEvents()
        localDataSource.saveEvents(events)
    }
}