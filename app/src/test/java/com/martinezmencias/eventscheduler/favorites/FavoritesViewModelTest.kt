package com.martinezmencias.eventscheduler.favorites

import app.cash.turbine.test
import com.martinezmencias.eventscheduler.testrules.CoroutinesTestRule
import com.martinezmencias.eventscheduler.testshared.sampleEvent
import com.martinezmencias.eventscheduler.ui.favorites.FavoritesViewModel
import com.martinezmencias.eventscheduler.usecases.GetFavoriteEventsUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class FavoritesViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Mock
    lateinit var getFavoriteEventsUseCase: GetFavoriteEventsUseCase

    private lateinit var vm: FavoritesViewModel

    private val favoriteEvents = listOf(sampleEvent.copy(id = "X", favorite = true))

    @Before
    fun setUp() {
        whenever(getFavoriteEventsUseCase()).thenReturn(flowOf(favoriteEvents))
        vm = FavoritesViewModel(getFavoriteEventsUseCase)
    }

    @Test
    fun `State is updated with current favorites immediately`() = runTest {
        vm.state.test {
            Assert.assertEquals(FavoritesViewModel.UiState(), awaitItem())
            Assert.assertEquals(
                FavoritesViewModel.UiState(favoriteEvents = favoriteEvents),
                awaitItem()
            )
            cancel()
        }
    }
}