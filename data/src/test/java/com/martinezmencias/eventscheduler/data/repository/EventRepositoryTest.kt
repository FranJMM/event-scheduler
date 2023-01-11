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
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class EventRepositoryTest {

    @Mock
    lateinit var localDataSource: EventLocalDataSource

    @Mock
    lateinit var remoteDataSource: EventRemoteDataSource

    @Mock
    lateinit var regionRepository: RegionRepository

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
}