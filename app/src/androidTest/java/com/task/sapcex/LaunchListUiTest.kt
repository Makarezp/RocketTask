package com.task.sapcex

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.intent.matcher.IntentMatchers.hasData
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.task.spacex.MainActivity
import com.task.spacex.R
import com.task.spacex.ui.launch_list.view_holders.LaunchViewHolder
import com.task.spacex.util.espressohelpers.EspressoIdlingResource
import org.hamcrest.core.AllOf.allOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class LaunchListUiTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
        Intents.init()
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
        Intents.release()
    }

    @Test
    fun loadsCompanyInfo() {
        val expectedText = getStringRes(
            R.string.company_info,
            "SpaceX",
            "Elon Musk",
            "2002",
            "9500",
            "3",
            "74000000000"
        )

        onView(withId(R.id.recycler))
            .check(matches(atPosition(1, hasDescendant(withText(expectedText)))))
    }

    @Test
    fun opensArticle() {
        val expectedArticle =
            "https://www.space.com/2196-spacex-inaugural-falcon-1-rocket-lost-launch.html"

        // when
        onView(withId(R.id.recycler)).perform(
            RecyclerViewActions.actionOnItemAtPosition<LaunchViewHolder>(
                3,
                click()
            )
        )
        onView(withId(R.id.article_row)).perform(click())

        // then
        intended(
            allOf(
                hasAction(Intent.ACTION_VIEW),
                hasData(expectedArticle)
            ),
        )
    }

    @Test
    fun opensVideo() {
        val expectedVideo =
            "https://www.youtube.com/watch?v=0a_00nJ_Y88"

        // when
        onView(withId(R.id.recycler)).perform(
            RecyclerViewActions.actionOnItemAtPosition<LaunchViewHolder>(
                3,
                click()
            )
        )
        onView(withId(R.id.video_row)).perform(click())

        // then
        intended(
            allOf(
                hasAction(Intent.ACTION_VIEW),
                hasData(expectedVideo)
            ),
        )
    }

    @Test
    fun opensWiki() {
        val expectedWiki =
            "https://en.wikipedia.org/wiki/DemoSat"

        // when
        onView(withId(R.id.recycler)).perform(
            RecyclerViewActions.actionOnItemAtPosition<LaunchViewHolder>(
                3,
                click()
            )
        )
        onView(withId(R.id.wiki_row)).perform(click())

        // then
        intended(
            allOf(
                hasAction(Intent.ACTION_VIEW),
                hasData(expectedWiki)
            ),
        )
    }
}
