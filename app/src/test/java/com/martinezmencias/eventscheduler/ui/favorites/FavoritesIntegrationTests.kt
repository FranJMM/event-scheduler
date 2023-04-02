package com.martinezmencias.eventscheduler.ui.favorites

import app.cash.turbine.test
import com.martinezmencias.eventscheduler.appTestShared.buildDatabaseEventsBasic
import com.martinezmencias.eventscheduler.appTestShared.buildDatabaseVenues
import com.martinezmencias.eventscheduler.appTestShared.buildEventRepositoryWith
import com.martinezmencias.eventscheduler.appTestShared.buildRemoteEvents
import com.martinezmencias.eventscheduler.data.database.EventBasicEntity
import com.martinezmencias.eventscheduler.data.database.VenueEntity
import com.martinezmencias.eventscheduler.data.server.RemoteEvent
import com.martinezmencias.eventscheduler.testrules.CoroutinesTestRule
import com.martinezmencias.eventscheduler.ui.favorites.FavoritesViewModel
import com.martinezmencias.eventscheduler.usecases.GetFavoriteEventsUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class FavoritesIntegrationTests {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Test
    fun `favorites are loaded from local source`() = runTest {
        val localEventBasicData =
            buildDatabaseEventsBasic("1", "2", "3", "4", "5").toMutableList().apply {
                replaceAll {
                    if (it.id == "2" || it.id == "4") {
                        it.copy(favorite = true)
                    } else {
                        it
                    }
                }
            }
        val localVenueData = buildDatabaseVenues("1")
        val remoteData = buildRemoteEvents("6", "7", "8")
        val vm = buildViewModelWith(
            localEventBasicData = localEventBasicData,
            localVenueData = localVenueData,
            remoteData = remoteData
        )

        vm.state.test {
            Assert.assertEquals(FavoritesViewModel.UiState(), awaitItem())

            val events = awaitItem().favoriteEvents!!
            Assert.assertEquals("Title 2", events[0].name)
            Assert.assertEquals("Title 4", events[1].name)

            cancel()
        }
    }

    private fun buildViewModelWith(
        localEventBasicData: List<EventBasicEntity>,
        localVenueData: List<VenueEntity>,
        remoteData: List<RemoteEvent>
    ): FavoritesViewModel {
        val eventsRepository = buildEventRepositoryWith(localEventBasicData, localVenueData, remoteData)
        val getFavoriteEventsUseCase = GetFavoriteEventsUseCase(eventsRepository)
        return FavoritesViewModel(getFavoriteEventsUseCase)
    }

}