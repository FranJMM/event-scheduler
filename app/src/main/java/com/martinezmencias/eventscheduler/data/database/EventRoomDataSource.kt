package com.martinezmencias.eventscheduler.data.database

import com.martinezmencias.eventscheduler.data.datasource.EventLocalDataSource
import com.martinezmencias.eventscheduler.domain.Event
import kotlinx.coroutines.flow.Flow

class EventRoomDataSource : EventLocalDataSource {

    override val events: Flow<List<Event>>
        get() = TODO("Not yet implemented")

}