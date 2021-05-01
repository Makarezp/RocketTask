package com.task.spacex.ui

import androidx.paging.PagingData
import com.task.spacex.UnitTestBase
import com.task.spacex.repository.FilterRepository
import com.task.spacex.repository.LaunchRepository
import com.task.spacex.repository.domain.FilterDomain
import com.task.spacex.repository.domain.LaunchDomain
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Test

class RocketListViewModelTest : UnitTestBase<RocketListViewModel>() {

    @MockK
    private lateinit var mockLaunchRepository: LaunchRepository

    @MockK
    private lateinit var mockFilterRepository: FilterRepository

    private var dispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()

    override fun beforeBuildSut() {
        Dispatchers.setMain(dispatcher)
    }

    override fun buildSut(): RocketListViewModel {
        return RocketListViewModel(mockLaunchRepository, mockFilterRepository)
    }

    override fun customiseFixture() {
        fixture.jFixture.customise()
            .sameInstance(FilterDomain.Status::class.java, FilterDomain.Status.All)
    }

    @After
    fun cleanDispatcher() {
        dispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `getLaunches emits when filtering changes`() = runBlockingTest {
        val fixFilterDomain1: FilterDomain = fixture()
        val fixFilterDomain2: FilterDomain = fixFilterDomain1
        val fixFilterDomain3: FilterDomain =
            fixFilterDomain1.copy(status = FilterDomain.Status.Failure)

        val fixtData1: PagingData<LaunchDomain> = PagingData.from(
            listOf(
                fixture(),
                fixture()
            )
        )

        val fixtData2: PagingData<LaunchDomain> = PagingData.from(
            listOf(
                fixture(),
                fixture()
            )
        )

        val filterFlow = MutableStateFlow(fixFilterDomain1)
        every { mockFilterRepository.getFilter() } returns filterFlow
        every { mockLaunchRepository.getLaunches(fixFilterDomain1) } returns flowOf(fixtData1)
        every { mockLaunchRepository.getLaunches(fixFilterDomain3) } returns flowOf(fixtData2)

        val actualData: MutableList<PagingData<LaunchDomain>> = mutableListOf()

        val job = launch {
            sut.getLaunches().collect {
                actualData += it
            }
        }

        filterFlow.emit(fixFilterDomain2)
        filterFlow.emit(fixFilterDomain3)

        assert(actualData.size == 2)
        verify(exactly = 1) {
            mockLaunchRepository.getLaunches(fixFilterDomain1)
            mockLaunchRepository.getLaunches(fixFilterDomain3)
        }
        job.cancel()
    }
}
