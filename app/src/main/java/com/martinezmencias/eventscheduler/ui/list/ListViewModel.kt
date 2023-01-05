package com.martinezmencias.eventscheduler.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.martinezmencias.eventscheduler.usecases.RequestEventsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ListViewModel(private val requestEventsUseCase: RequestEventsUseCase) : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        
        viewModelScope.launch {
            val remoteEvents = requestEventsUseCase()
            _state.update { UiState(events = remoteEvents) }
        }
    }

    data class UiState(val events: List<com.martinezmencias.eventscheduler.domain.Event>? = null)
}