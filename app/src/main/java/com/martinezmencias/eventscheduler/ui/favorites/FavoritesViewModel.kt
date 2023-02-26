package com.martinezmencias.eventscheduler.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.martinezmencias.eventscheduler.data.util.toError
import com.martinezmencias.eventscheduler.domain.Error
import com.martinezmencias.eventscheduler.domain.Event
import com.martinezmencias.eventscheduler.usecases.GetFavoriteEventsUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val getFavoriteEventsUseCase: GetFavoriteEventsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            getFavoriteEventsUseCase()
                .catch { cause -> _state.update { it.copy(error = cause.toError()) } }
                .collect() { favoriteEvents ->
                    _state.update {
                        UiState(
                            favoriteEvents = favoriteEvents,
                            showFavoritesEmptyMessage = favoriteEvents.isEmpty()
                        )
                    }
                }
        }
    }

    data class UiState(
        val favoriteEvents: List<Event>? = null,
        val showFavoritesEmptyMessage: Boolean = false,
        val error: Error? = null
    )
}