package com.martinezmencias.eventscheduler.ui.list

import app.cash.turbine.test
import com.martinezmencias.eventscheduler.appTestShared.*
import com.martinezmencias.eventscheduler.testrules.CoroutinesTestRule
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
import kotlin.test.assertFalse
import kotlin.test.assertTrue

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
            assertEquals(UiState(), awaitItem().also { println("awaitItem 1: $it") })
            assertEquals(UiState(events = emptyList(), loading = false), awaitItem().also { println("awaitItem 2: $it") })
            assertEquals(UiState(events = emptyList(), loading = true), awaitItem().also { println("awaitItem 3: $it") })
            assertEquals(UiState(events = emptyList(), loading = false), awaitItem().also { println("awaitItem 4: $it") })


            val finalUiState = awaitItem().also { println("awaitItem 4: $it") }
            assertFalse {  finalUiState.loading }

            val events = finalUiState.events!!
            assertEquals("Name a", events[0].name)
            assertEquals("Name b", events[1].name)
            assertEquals("Name c", events[2].name)

            cancel()
        }
    }

    @Test
    fun `data is loaded from local source when available`() = runTest {
        val localEventBasicData = buildDatabaseEventsBasic("1", "2", "3")
        val localVenueData = buildDatabaseVenues("1")
        val remoteData = buildRemoteEvents("4", "5", "6")
        val vm = buildViewModelWith(
            localEventBasicData = localEventBasicData,
            localVenueData = localVenueData,
            remoteData = remoteData
        )

        vm.state.test {
            assertEquals(UiState(), awaitItem())

            val events = awaitItem().events!!
            assertEquals("Title 1", events[0].name)
            assertEquals("Title 2", events[1].name)
            assertEquals("Title 3", events[2].name)

            cancel()
        }
    }


    private fun buildViewModelWith(
        localEventBasicData: List<EventBasicEntity>,
        localVenueData: List<VenueEntity>,
        remoteData: List<RemoteEvent>
    ): ListViewModel {
        val eventsRepository = buildEventRepositoryWith(localEventBasicData, localVenueData, remoteData)
        val getEventsUseCase = GetEventsUseCase(eventsRepository)
        val requestEventsUseCase = RequestEventsUseCase(eventsRepository)
        return ListViewModel(getEventsUseCase, requestEventsUseCase)
    }
}