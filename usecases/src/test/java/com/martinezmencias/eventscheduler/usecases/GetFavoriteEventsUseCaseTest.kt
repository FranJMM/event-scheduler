package com.martinezmencias.eventscheduler.usecases

import com.martinezmencias.eventscheduler.data.repository.EventRepository
import com.martinezmencias.eventscheduler.testshared.sampleEvent
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.mockito.Mock
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class GetFavoriteEventsUseCaseTest {

    @Mock
    lateinit var eventRepository: EventRepository

    @Test
    fun `Invoke calls event repository`(): Unit = runBlocking {
        val sampleEvents = flowOf(
            listOf(
                sampleEvent.copy(id = "1", favorite = false),
                sampleEvent.copy(id = "2", favorite = true)
            )
        )
        val sampleFavoriteEvents = flowOf(listOf(sampleEvent.copy(id = "2", favorite = true)))

        eventRepository = mock() {
            on { events } doReturn sampleEvents
            on { favoriteEvents } doReturn sampleFavoriteEvents
        }

        val getFavoriteEventsUseCase = GetFavoriteEventsUseCase(eventRepository)

        val result = getFavoriteEventsUseCase()

        Assert.assertEquals(sampleFavoriteEvents, result)
    }
}