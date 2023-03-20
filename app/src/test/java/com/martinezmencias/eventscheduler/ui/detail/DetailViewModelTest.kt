package com.martinezmencias.eventscheduler.ui.detail

import app.cash.turbine.test
import com.martinezmencias.eventscheduler.testrules.CoroutinesTestRule
import com.martinezmencias.eventscheduler.testshared.sampleEvent
import com.martinezmencias.eventscheduler.ui.detail.DetailViewModel.UiState
import com.martinezmencias.eventscheduler.usecases.FindEventUseCase
import com.martinezmencias.eventscheduler.usecases.SwitchEventFavoriteUseCase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Mock
    lateinit var findEventUseCase: FindEventUseCase

    @Mock
    lateinit var switchEventFavoriteUseCase: SwitchEventFavoriteUseCase

    private lateinit var vm: DetailViewModel

    private val event = sampleEvent.copy(id = "1")

    @Before
    fun setup() {
        whenever(findEventUseCase("1")).thenReturn(flowOf(event))
        vm = DetailViewModel("1", findEventUseCase, switchEventFavoriteUseCase)
    }

    @Test
    fun `UI is updated with the event on start`() = runTest {
        vm.state.test {
            assertEquals(UiState(), awaitItem())
            assertEquals(UiState(event = event), awaitItem())
            cancel()
        }
    }

    @Test
    fun `Favorite action calls the corresponding use case`() = runTest {
        vm.onFavoriteClicked()
        runCurrent()

        verify(switchEventFavoriteUseCase).invoke(event)
    }
}