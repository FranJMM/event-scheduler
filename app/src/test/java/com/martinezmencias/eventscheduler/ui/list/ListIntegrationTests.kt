package com.martinezmencias.eventscheduler.ui.list

import app.cash.turbine.test
import com.martinezmencias.eventscheduler.appTestShared.buildEventRepositoryWith
import com.martinezmencias.eventscheduler.testrules.CoroutinesTestRule
import com.martinezmencias.eventscheduler.appTestShared.buildRemoteEvents
import com.martinezmencias.eventscheduler.data.database.EventBasicEntity
import com.martinezmencias.eventscheduler.data.database.VenueEntity
import com.martinezmencias.eventscheduler.data.server.RemoteEvent
import com.martinezmencias.eventscheduler.ui.list.ListViewModel.UiState
import com.martinezmencias.eventscheduler.usecases.GetEventsUseCase
import com.martinezmencias.eventscheduler.usecases.RequestEventsUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ListIntegrationTests {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Test
    fun `data is loaded from server when local source is empty`() = runTest {
        val remoteData = buildRemoteEvents("a", "b", "c")

        val vm = buildViewModelWith(
            localEventBasicData = emptyList(),
            localVenueData = emptyList(),
            remoteData = remoteData
        )

        vm.onUiReady()

        vm.state.test {
            assertEquals(UiState(), awaitItem())
            assertEquals(UiState(events = emptyList()), awaitItem())



            val events = awaitItem().events!!
            assertEquals("Name a", events[0].name)
            assertEquals("Name b", events[1].name)
            assertEquals("Name c", events[2].name)

            cancel()
        }
    }

    private fun buildViewModelWith(
        localEventBasicData: List<EventBasicEntity>,
        localVenueData: List<VenueEntity>,
        remoteData: List<RemoteEvent>
    ): ListViewModel {
        val eventsRepository = buildEventRepositoryWith(localEventBasicData, localVenueData, remoteData)
        val getPopularMoviesUseCase = GetEventsUseCase(eventsRepository)
        val requestPopularMoviesUseCase = RequestEventsUseCase(eventsRepository)
        return ListViewModel(getPopularMoviesUseCase, requestPopularMoviesUseCase)
    }
}