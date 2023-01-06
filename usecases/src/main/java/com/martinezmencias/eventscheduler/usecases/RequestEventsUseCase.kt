package com.martinezmencias.eventscheduler.usecases

import com.martinezmencias.eventscheduler.data.repository.EventRepository
import com.martinezmencias.eventscheduler.domain.Error

class RequestEventsUseCase(private val eventsRepository: EventRepository) {

    suspend operator fun invoke(): Error? = eventsRepository.requestEvents()
}