package com.task.spacex.repository

import androidx.paging.Pager
import androidx.paging.PagingData
import com.task.spacex.UnitTestBase
import com.task.spacex.repository.db.LaunchEntity
import com.task.spacex.repository.domain.FilterDomain
import com.task.spacex.repository.domain.LaunchDomain
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

class LaunchRepositoryTest : UnitTestBase<LaunchRepository>() {

    @MockK
    private lateinit var mockPagerFactory: PagerFactory

    @MockK
    private lateinit var mockDomainMapper: LaunchDomainMapper

    override fun customiseFixture() {
        fixture.jFixture.customise()
            .sameInstance(FilterDomain.Status::class.java, FilterDomain.Status.Success)
    }

    override fun buildSut(): LaunchRepository {
        return LaunchRepository(
            mockPagerFactory,
            mockDomainMapper
        )
    }

    @Test
    fun getLaunches() = runBlockingTest {
        val fixtFilter: FilterDomain = fixture()

        val fixtPagingData: PagingData<LaunchEntity> = PagingData.empty()

        val mockPager: Pager<Int, LaunchEntity> = mockk()
        every { mockPager.flow } returns flowOf(fixtPagingData)
        every { mockPagerFactory.newPager(fixtFilter) } returns mockPager

        val actual:  MutableList<PagingData<LaunchDomain>> = mutableListOf()
        val job = launch {
            sut.getLaunches(fixtFilter)
                .collect {
                    actual += it
                }
        }

        assertEquals(actual.size, 1)

        job.cancel()
    }

}
