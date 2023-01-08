package com.martinezmencias.eventscheduler.data.repository

import com.martinezmencias.eventscheduler.data.datasource.EventLocalDataSource
import com.martinezmencias.eventscheduler.data.datasource.EventRemoteDataSource
import com.martinezmencias.eventscheduler.domain.Error

class EventRepository(
    private val regionRepository: RegionRepository,
    private val localDataSource: EventLocalDataSource,
    private val remoteDataSource: EventRemoteDataSource
) {

    val events = localDataSource.events

    fun findEventById(id: String) = localDataSource.findEventById(id)

    suspend fun requestEvents(): Error? {
        if (localDataSource.isEmpty()) {
            val result = remoteDataSource.requestEvents(regionRepository.findLastRegion())
            result.fold({
                return it
            }, { events ->
                localDataSource.saveEvents(events)
            })
        }
        return null
    }
}