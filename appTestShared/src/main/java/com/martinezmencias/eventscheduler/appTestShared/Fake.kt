package com.martinezmencias.eventscheduler.appTestShared

import com.martinezmencias.eventscheduler.data.PermissionChecker
import com.martinezmencias.eventscheduler.data.database.*
import com.martinezmencias.eventscheduler.data.datasource.LocationDataSource
import com.martinezmencias.eventscheduler.data.server.RemoteResult
import com.martinezmencias.eventscheduler.data.server.RemoteService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeEventDao(
    eventsBasic: List<EventBasicEntity> = emptyList(),
    fakeVenueDao: FakeVenueDao
) : EventDao {

    init {
        fakeVenueDao.onVenuesInserted = ::onVenuesInserted
    }

    private var inMemoryVenuesValue = fakeVenueDao.inMemoryVenues.value

    private val inMemoryEvents = MutableStateFlow(eventsBasic.merge(inMemoryVenuesValue))

    private lateinit var findEventFlow: MutableStateFlow<EventEntity>

    override fun getAll(): Flow<List<EventEntity>> = inMemoryEvents

    override fun findById(id: String): Flow<EventEntity> {
        findEventFlow = MutableStateFlow(inMemoryEvents.value.first { it.eventBasic.id == id })
        return findEventFlow
    }

    override suspend fun eventCount(): Int = inMemoryEvents.value.size

    override suspend fun insertEventsBasic(events: List<EventBasicEntity>) {
        inMemoryEvents.value = events.merge(inMemoryVenuesValue)
        updateFindEventFlow(inMemoryEvents.value)
    }

    override suspend fun updateEventBasic(eventUpdated: EventBasicEntity) {
        inMemoryEvents.value.toMutableList().replaceAll { eventEntity ->
            if (eventUpdated.id == eventEntity.eventBasic.id) {
                eventEntity.eventBasic.merge(inMemoryVenuesValue)
            } else {
                eventEntity
            }
        }
        updateFindEventFlow(inMemoryEvents.value)
    }

    private fun List<EventBasicEntity>.merge(venues: List<VenueEntity>?) = this.map { eventBasicEntity ->
        eventBasicEntity.merge(venues)
    }

    private fun EventBasicEntity.merge(venues: List<VenueEntity>?): EventEntity {
        val venue =  venues?.find { venue -> this.venueId == venue.id } ?: VenueEntity("", "", "", "", "", "")
        return EventEntity(eventBasic = this, venue = venue)
    }

    private fun updateFindEventFlow(events: List<EventEntity>) {
        if (::findEventFlow.isInitialized) {
            events.firstOrNull() { findEventFlow.value.eventBasic.id == it.eventBasic.id }
                ?.let { findEventFlow.value = it }
        }
    }

    private fun onVenuesInserted(venues: List<VenueEntity>) {
        inMemoryVenuesValue = venues
        inMemoryEvents.value = inMemoryEvents.value.map { it.eventBasic }.merge(inMemoryVenuesValue)
        updateFindEventFlow(inMemoryEvents.value)
    }
}

class FakeVenueDao(venues: List<VenueEntity>): VenueDao {
    val inMemoryVenues = MutableStateFlow(venues)

    var onVenuesInserted: ((List<VenueEntity>) -> Unit)? = null

    override suspend fun insertVenues(venues: List<VenueEntity>) {
        inMemoryVenues.value = venues
        onVenuesInserted?.let {
            it(venues)
        }
    }
}

class FakeRemoteService(private val remoteResult: RemoteResult) : RemoteService {
    override suspend fun requestEvents(
        apiKey: String,
        countryCode: String,
        classificationName: String
    ) = remoteResult
}

class FakePermissionChecker : PermissionChecker {
    override fun check(permission: PermissionChecker.Permission) = true
}

class FakeLocationDataSource : LocationDataSource {
    override suspend fun findLastRegion(): String = "US"
}
