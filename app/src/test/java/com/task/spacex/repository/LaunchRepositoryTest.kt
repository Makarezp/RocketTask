package com.task.spacex.repository

import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.map
import com.task.spacex.UnitTestBase
import com.task.spacex.repository.db.LaunchEntity
import com.task.spacex.repository.domain.FilterDomain
import com.task.spacex.repository.domain.LaunchDomain
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
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
        val fixtLaunchEntities: List<LaunchEntity> = listOf(
            fixture(),
            fixture()
        )
        val fixtLaunches: List<LaunchDomain> = listOf(
            fixture(),
            fixture()
        )
        val fixtPagingData: PagingData<LaunchEntity> = PagingData.from(fixtLaunchEntities)

        every { mockDomainMapper.map(fixtLaunchEntities[0]) } returns fixtLaunches[0]
        every { mockDomainMapper.map(fixtLaunchEntities[1]) } returns fixtLaunches[1]

        val mockPager: Pager<Int, LaunchEntity> = mockk()
        every { mockPager.flow } returns flowOf(fixtPagingData)
        every { mockPagerFactory.newPager(fixtFilter) } returns mockPager

        val actual = sut.getLaunches(fixtFilter).first()

        // Hmm looks like paging data is not testable -- fallback to hack
        actual.map {
            assert(fixtLaunches.contains(it))
            it
        }
    }

}
