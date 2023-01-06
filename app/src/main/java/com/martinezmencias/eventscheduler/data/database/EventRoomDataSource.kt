package com.martinezmencias.eventscheduler.data.database

import com.martinezmencias.eventscheduler.data.datasource.EventLocalDataSource
import com.martinezmencias.eventscheduler.domain.Event
import kotlinx.coroutines.Dispatchers
import com.martinezmencias.eventscheduler.data.database.Event as EventEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class EventRoomDataSource(private val eventDao: EventDao) : EventLocalDataSource {

    override val events: Flow<List<Event>> = eventDao.getAll().map { it.toDomainModel() }

    override suspend fun isEmpty(): Boolean = eventDao.eventCount() == 0

    override suspend fun saveEvents(events: List<Event>) =
        eventDao.insertEvents(events.toEntityModel())
}

private fun EventEntity.toDomainModel() = Event(id, name, imageUrl)

private fun List<EventEntity>.toDomainModel() = this.map { it.toDomainModel() }

private fun Event.toEntityModel() = EventEntity(id = id, name = name, imageUrl = imageUrl)

private fun List<Event>.toEntityModel() = this.map { it.toEntityModel() }