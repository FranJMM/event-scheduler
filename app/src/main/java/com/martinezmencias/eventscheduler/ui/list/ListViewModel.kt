package com.martinezmencias.eventscheduler.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.martinezmencias.eventscheduler.data.datasource.EventRemoteDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ListViewModel : ViewModel(), KoinComponent{

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    private val eventRemoteDataSource: EventRemoteDataSource by inject()

    init {
        
        viewModelScope.launch {
            val remoteEvents = eventRemoteDataSource.requestEvents()
            _state.update { UiState(events = remoteEvents) }
        }
    }

    data class UiState(val events: List<com.martinezmencias.eventscheduler.domain.Event>? = null)
}