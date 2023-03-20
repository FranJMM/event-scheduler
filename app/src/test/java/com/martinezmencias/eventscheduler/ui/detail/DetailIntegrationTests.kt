package com.martinezmencias.eventscheduler.ui.detail

import app.cash.turbine.test
import com.martinezmencias.eventscheduler.appTestShared.buildDatabaseEventsBasic
import com.martinezmencias.eventscheduler.appTestShared.buildDatabaseVenues
import com.martinezmencias.eventscheduler.appTestShared.buildEventRepositoryWith
import com.martinezmencias.eventscheduler.data.database.EventBasicEntity
import com.martinezmencias.eventscheduler.data.database.VenueEntity
import com.martinezmencias.eventscheduler.data.server.RemoteEvent
import com.martinezmencias.eventscheduler.testrules.CoroutinesTestRule
import com.martinezmencias.eventscheduler.ui.detail.DetailViewModel.UiState
import com.martinezmencias.eventscheduler.usecases.FindEventUseCase
import com.martinezmencias.eventscheduler.usecases.SwitchEventFavoriteUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class DetailIntegrationTests {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Test
    fun `UI is updated with the event on start`() = runTest {
        val localEventBasicData= buildDatabaseEventsBasic("1", "2", "3")
        val localVenueData = buildDatabaseVenues("1")
        val vm = buildViewModelWith(
            "1",
            localEventBasicData = localEventBasicData,
            localVenueData = localVenueData
        )

        vm.state.test {
            Assert.assertEquals(UiState(), awaitItem())
            Assert.assertEquals("1", awaitItem().event!!.id)
            cancel()
        }
    }

    @Test
    fun `Favorite is updated in local data source`() = runTest {
        val localEventBasicData= buildDatabaseEventsBasic("1", "2", "3")
        val localVenueData = buildDatabaseVenues("1")
        val vm = buildViewModelWith(
            "1",
            localEventBasicData = localEventBasicData,
            localVenueData = localVenueData
        )

        vm.onFavoriteClicked()

        vm.state.test {
            Assert.assertEquals(UiState(), awaitItem())
            Assert.assertEquals(false, awaitItem().event!!.favorite)
            Assert.assertEquals(true, awaitItem().event!!.favorite)
            cancel()
        }
    }

    private fun buildViewModelWith(
        id: String,
        localEventBasicData: List<EventBasicEntity> = emptyList(),
        localVenueData: List<VenueEntity> = emptyList(),
        remoteData: List<RemoteEvent> = emptyList()
    ): DetailViewModel {
        val eventsRepository = buildEventRepositoryWith(localEventBasicData, localVenueData, remoteData)
        val findEventUseCase = FindEventUseCase(eventsRepository)
        val switchEventFavoriteUseCase = SwitchEventFavoriteUseCase(eventsRepository)
        return DetailViewModel(id, findEventUseCase, switchEventFavoriteUseCase)
    }
}