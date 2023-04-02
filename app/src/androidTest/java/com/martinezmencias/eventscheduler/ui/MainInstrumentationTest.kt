package com.martinezmencias.eventscheduler.ui

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.rule.GrantPermissionRule
import com.martinezmencias.eventscheduler.R
import com.martinezmencias.eventscheduler.data.server.MockWebServerRule
import com.martinezmencias.eventscheduler.data.server.fromJson
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.java.KoinJavaComponent.inject

@ExperimentalCoroutinesApi
class MainInstrumentationTest {

    @get:Rule(order = 0)
    val locationPermissionRule: GrantPermissionRule = GrantPermissionRule.grant(
        "android.permission.ACCESS_COARSE_LOCATION"
    )

    @get:Rule(order = 1)
    val mockWebServerRule = MockWebServerRule()

    @get:Rule(order = 2)
    val activityRule = ActivityScenarioRule(NavHostActivity::class.java)

    private val okHttpClient: OkHttpClient by inject(OkHttpClient::class.java)

    @Before
    fun setUp() {
        mockWebServerRule.server.enqueue(
            MockResponse().fromJson("events.json")
        )

        val resource = OkHttp3IdlingResource.create("OkHttp", okHttpClient)
        IdlingRegistry.getInstance().register(resource)
    }

    @Test
    fun click_favorites_button_navigates_to_favorites_and_shows_favorites_empty_message() {
        Espresso.onView(withId(R.id.action_go_favorites)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.favorites_empty_message))
            .check(ViewAssertions.matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
    }

    @Test
    fun click_an_event_navigates_to_detail() {
        Espresso.onView(withId(R.id.recycler))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    0, ViewActions.click()
                )
            )
        Espresso.onView(withId(R.id.fragment_container_detail_view))
            .check(ViewAssertions.matches(ViewMatchers.hasDescendant(ViewMatchers.withText("frantest"))))

    }
}