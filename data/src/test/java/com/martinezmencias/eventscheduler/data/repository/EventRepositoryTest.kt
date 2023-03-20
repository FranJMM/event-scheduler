package com.martinezmencias.eventscheduler.data.repository

import arrow.core.right
import com.martinezmencias.eventscheduler.data.datasource.EventLocalDataSource
import com.martinezmencias.eventscheduler.data.datasource.EventRemoteDataSource
import com.martinezmencias.eventscheduler.testshared.sampleEvent
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.argThat
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class EventRepositoryTest {

    @Mock
    private lateinit var localDataSource: EventLocalDataSource

    @Mock
    private lateinit var remoteDataSource: EventRemoteDataSource

    @Mock
    private lateinit var regionRepository: RegionRepository

    private lateinit var eventRepository: EventRepository

    private val localEvents = flowOf(listOf(sampleEvent.copy(id = "X")))

    @Before
    fun setUp() {
        whenever(localDataSource.events).thenReturn(localEvents)
        eventRepository = EventRepository(regionRepository, localDataSource, remoteDataSource)
    }

    @Test
    fun `Events are taken from local data source if available`(): Unit = runBlocking {

        val result = eventRepository.events

        assertEquals(localEvents, result)
    }

    @Test
    fun `Events are saved to local data source when it's empty`(): Unit = runBlocking {
        val remoteEvents = listOf(sampleEvent.copy(id = "Y"))
        whenever(localDataSource.isEmpty()).thenReturn(true)
        whenever(regionRepository.findLastRegion()).thenReturn(RegionRepository.DEFAULT_REGION)
        whenever(remoteDataSource.requestEvents(any())).thenReturn(remoteEvents.right())

        eventRepository.requestEvents()

        verify(localDataSource).saveEvents(remoteEvents)
    }

    @Test
    fun `Finding an event by id is done in local data source`(): Unit = runBlocking {
        val event = flowOf(sampleEvent.copy(id = "5"))
        whenever(localDataSource.findEventById("5")).thenReturn(event)

        val result = eventRepository.findEventById("5")

        assertEquals(event, result)
    }

    @Test
    fun `Switching favorite updates local data source`(): Unit = runBlocking {
        val event = sampleEvent.copy(id = "3")

        eventRepository.switchFavorite(event)

        verify(localDataSource).updateEvent(argThat { id == "3" })
    }

    @Test
    fun `Switching favorite marks as favorite an unfavorite movie`(): Unit = runBlocking {
        val event = sampleEvent.copy(id = "1", favorite = false)

        eventRepository.switchFavorite(event)

        verify(localDataSource).updateEvent(argThat { favorite })
    }

    @Test
    fun `Switching favorite marks as unfavorite a favorite event`(): Unit = runBlocking {
        val event = sampleEvent.copy(favorite = true)

        eventRepository.switchFavorite(event)

        verify(localDataSource).updateEvent(argThat { !favorite })
    }
}