package com.martinezmencias.eventscheduler.data.database

import com.martinezmencias.eventscheduler.data.datasource.EventLocalDataSource
import com.martinezmencias.eventscheduler.domain.Event
import com.martinezmencias.eventscheduler.domain.Venue
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class EventRoomDataSource(private val eventDao: EventDao, private val venueDao: VenueDao) :
    EventLocalDataSource {

    override val events: Flow<List<Event>> = eventDao.getAll().map { it.toDomainModel() }

    override fun findEventById(id: String) = eventDao.findById(id).map { it.toDomainModel() }

    override suspend fun isEmpty(): Boolean = eventDao.eventCount() == 0

    override suspend fun saveEvents(events: List<Event>) {
        venueDao.insertVenues(events.toEntityVenueModel())
        eventDao.insertEventsBasic(events.toEntityBasicModel())
    }
}

private fun EventEntity.toDomainModel() =
    Event(
        id = eventBasic.id,
        name = eventBasic.name,
        imageUrl = eventBasic.imageUrl,
        startTime = eventBasic.startTime,
        salesUrl = eventBasic.salesUrl,
        salesStartTime = eventBasic.salesStartTime,
        venue = venue.toDomainModel()
    )

private fun List<EventEntity>.toDomainModel() = this.map { it.toDomainModel() }

private fun Event.toEntityBasicModel() =
    EventBasicEntity(
        id = id,
        name = name,
        imageUrl = imageUrl,
        startTime = startTime,
        salesUrl = salesUrl,
        salesStartTime = salesStartTime,
        venueId = venue.id
    )

private fun List<Event>.toEntityVenueModel() =
    this.map { it.venue }.distinctBy { it.id }.toEntityModel()

private fun List<Event>.toEntityBasicModel() = this.map { it.toEntityBasicModel() }

private fun Venue.toEntityModel() = VenueEntity(
    id = id,
    name = name,
    city = city,
    state = state,
    country = country,
    address = address
)

private fun List<Venue>.toEntityModel() = this.map { it.toEntityModel() }

private fun VenueEntity.toDomainModel() = Venue(
    id = id,
    name = name,
    city = city,
    state = state,
    country = country,
    address = address
)