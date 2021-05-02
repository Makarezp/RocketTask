package com.task.spacex.ui.action_sheet

import com.flextrade.jfixture.annotations.Fixture
import com.task.spacex.UnitTestBase
import com.task.spacex.repository.LaunchRepository
import com.task.spacex.repository.domain.LaunchDomain
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Test

class LaunchActionsViewModelTest : UnitTestBase<LaunchActionsViewModel>() {

    @MockK
    private lateinit var mockLaunchRepo: LaunchRepository

    @Fixture
    private lateinit var fixtLaunchId: String

    @Fixture
    private lateinit var fixtLaunch: LaunchDomain

    private var dispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()


    override fun buildSut(): LaunchActionsViewModel {
        return LaunchActionsViewModel(fixtLaunchId, mockLaunchRepo)
    }

    override fun before() {
        Dispatchers.setMain(dispatcher)
        coEvery { mockLaunchRepo.getLaunch(fixtLaunchId) } returns fixtLaunch
    }

    @After
    fun cleanDispatcher() {
        dispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `emits article link onArticleClicked()`() = runBlockingTest {
        var link: String? = null
        val job = launch {
            sut.openLinkAction.collect {
                link = it
            }
        }

        sut.onArticleClicked()

        assertEquals(fixtLaunch.article, link)

        job.cancel()
    }

    @Test
    fun `emits wiki link onWikiClicked()`() = runBlockingTest {
        var link: String? = null
        val job = launch {
            sut.openLinkAction.collect {
                link = it
            }
        }

        sut.onWikiClicked()

        assertEquals(fixtLaunch.wikipedia, link)

        job.cancel()
    }

    @Test
    fun `emits video link onVideoClicked()`() = runBlockingTest {
        var link: String? = null
        val job = launch {
            sut.openLinkAction.collect {
                link = it
            }
        }

        sut.onWikiClicked()

        assertEquals(fixtLaunch.wikipedia, link)

        job.cancel()
    }
}
