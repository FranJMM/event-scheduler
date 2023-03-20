package com.martinezmencias.eventscheduler.usecases

import com.martinezmencias.eventscheduler.data.repository.EventRepository
import com.martinezmencias.eventscheduler.testshared.sampleEvent
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.Mock
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class SwitchEventFavoriteUseCaseTest {

    @Mock
    lateinit var eventRepository: EventRepository

    @Test
    fun `Invoke calls event repository`(): Unit = runBlocking {
        val event = sampleEvent.copy(id = "1")
        eventRepository = mock()

        val switchEventFavoriteUseCase = SwitchEventFavoriteUseCase(eventRepository)

        switchEventFavoriteUseCase(event)

        verify(eventRepository).switchFavorite(event)
    }
}
