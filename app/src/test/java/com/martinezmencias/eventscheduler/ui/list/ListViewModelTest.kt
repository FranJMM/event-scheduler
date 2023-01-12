package com.martinezmencias.eventscheduler.ui.list

import app.cash.turbine.test
import com.martinezmencias.eventscheduler.testrules.CoroutinesTestRule
import com.martinezmencias.eventscheduler.testshared.sampleEvent
import com.martinezmencias.eventscheduler.ui.list.ListViewModel.UiState
import com.martinezmencias.eventscheduler.usecases.GetEventsUseCase
import com.martinezmencias.eventscheduler.usecases.RequestEventsUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ListViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Mock
    lateinit var getEventsUseCase: GetEventsUseCase

    @Mock
    lateinit var requestEventsUseCase: RequestEventsUseCase

    private lateinit var vm: ListViewModel

    private val events = listOf(sampleEvent.copy(id = "X"))

    @Before
    fun setUp() {
        whenever(getEventsUseCase()).thenReturn(flowOf(events))
        vm = ListViewModel(getEventsUseCase, requestEventsUseCase)
    }

    @Test
    fun `State is updated with current cached content immediately`() = runTest {
        vm.state.test {
            assertEquals(UiState(), awaitItem())
            assertEquals(UiState(events = events), awaitItem())
            cancel()
        }
    }
}