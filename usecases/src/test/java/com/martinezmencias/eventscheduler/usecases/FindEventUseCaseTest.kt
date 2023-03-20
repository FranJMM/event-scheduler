package com.martinezmencias.eventscheduler.usecases

import com.martinezmencias.eventscheduler.data.repository.EventRepository
import com.martinezmencias.eventscheduler.testshared.sampleEvent
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.Mock
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class FindEventUseCaseTest {

    @Mock
    lateinit var eventRepository: EventRepository

    private lateinit var findEventUseCase: FindEventUseCase

    @Test
    fun `Invoke calls event repository`(): Unit = runBlocking {
        val event = flowOf(sampleEvent.copy(id = "1"))

        eventRepository = mock() { on { findEventById("1")} doReturn event }

        findEventUseCase = FindEventUseCase(eventRepository)

        val result = findEventUseCase("1")

        assertEquals(event, result)
    }
}