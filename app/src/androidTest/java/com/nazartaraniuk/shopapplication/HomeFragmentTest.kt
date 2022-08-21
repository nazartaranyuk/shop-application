package com.nazartaraniuk.shopapplication

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import com.nazartaraniuk.shopapplication.presentation.adapters.CategoriesListSmallAdapterDelegate
import com.nazartaraniuk.shopapplication.presentation.adapters.DelegationAdapter
import com.nazartaraniuk.shopapplication.presentation.adapters.DisplayableItem
import org.hamcrest.Matcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
@LargeTest
class HomeFragmentTest {

    @get:Rule
    val activityTestRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun is_RootListEnabled_onAppLaunch() {
        onView(withId(R.id.rv_root_list))
            .check(matches(isEnabled()))
    }

    @Test
    fun test_SeeAll_navigation_toExploreFragment() {
        onView(isRoot()).perform(waitFor(5000))
        onView(withId(R.id.rv_root_list))
            .perform(
                actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    2, click()
                )
            )
    }

    @Test
    fun test_navigateToProductPageFragment() {
        onView(isRoot()).perform(waitFor(5000))
        onView(withId(R.id.rv_root_list))
            .perform(
                actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    2, click()
                )
            )
    }

    private fun waitFor(delay: Long): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> = isRoot()
            override fun getDescription(): String = "wait for $delay milliseconds"
            override fun perform(uiController: UiController, v: View?) {
                uiController.loopMainThreadForAtLeast(delay)
            }
        }
    }
}