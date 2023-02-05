package com.martinezmencias.eventscheduler.ui

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.rule.GrantPermissionRule
import com.martinezmencias.eventscheduler.R
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainInstrumentationTest {

    @get:Rule(order = 0)
    val locationPermissionRule: GrantPermissionRule = GrantPermissionRule.grant(
        "android.permission.ACCESS_COARSE_LOCATION"
    )

    @get:Rule(order = 1)
    val activityRule = ActivityScenarioRule(NavHostActivity::class.java)

    @Test
    fun click_an_event_navigates_to_detail() {

        Espresso.onView(withId(R.id.recycler))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    7, ViewActions.click()
                )
            )

        Espresso.onView(withId(R.id.fragment_container_detail_view))
            .check(ViewAssertions.matches(ViewMatchers.hasDescendant(ViewMatchers.withText("La Cueva Music Festival"))))

    }
}