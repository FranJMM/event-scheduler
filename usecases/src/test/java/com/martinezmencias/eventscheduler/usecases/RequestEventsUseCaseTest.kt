package com.martinezmencias.eventscheduler.usecases

import com.martinezmencias.eventscheduler.data.repository.EventRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify

@RunWith(MockitoJUnitRunner::class)
class RequestEventsUseCaseTest {

    @Mock
    private lateinit var eventRepository: EventRepository

    private lateinit var requestEventsUseCase: RequestEventsUseCase

    @Before
    fun setUp() {
        requestEventsUseCase = RequestEventsUseCase(eventRepository)
    }

    @Test
    fun `Invoke calls event repository`(): Unit = runBlocking {
        requestEventsUseCase()
        verify(eventRepository).requestEvents()
    }
}