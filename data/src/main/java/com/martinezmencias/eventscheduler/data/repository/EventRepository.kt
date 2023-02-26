package com.martinezmencias.eventscheduler.data.repository

import com.martinezmencias.eventscheduler.data.datasource.EventLocalDataSource
import com.martinezmencias.eventscheduler.data.datasource.EventRemoteDataSource
import com.martinezmencias.eventscheduler.domain.Error
import com.martinezmencias.eventscheduler.domain.Event

class EventRepository(
    private val regionRepository: RegionRepository,
    private val localDataSource: EventLocalDataSource,
    private val remoteDataSource: EventRemoteDataSource
) {

    val events = localDataSource.events

    val favoriteEvents = localDataSource.favoriteEvents

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

    suspend fun switchFavorite(event: Event) {
        val updatedEvent = event.copy(favorite = !event.favorite)
        localDataSource.updateEvent(updatedEvent)
    }
}