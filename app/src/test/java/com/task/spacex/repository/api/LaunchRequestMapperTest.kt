package com.task.spacex.repository.api

import com.flextrade.jfixture.annotations.Fixture
import com.task.spacex.UnitTestBase
import com.task.spacex.repository.domain.FilterDomain
import com.task.spacex.repository.domain.FilterDomain.SortOrder
import com.task.spacex.repository.domain.FilterDomain.Status
import junit.framework.Assert.*
import org.junit.Test

class LaunchRequestMapperTest : UnitTestBase<LaunchRequestMapper>() {

    @Fixture
    private lateinit var fixtFilterDomain: FilterDomain
    private var fixPageSize: Int = fixture()
    private var fixtPage: Int? = fixture()

    override fun buildSut(): LaunchRequestMapper {
        return LaunchRequestMapper()
    }

    override fun customiseFixture() {
        fixture.jFixture.customise()
            .sameInstance(Status::class.java, Status.All)
        fixture.jFixture.customise()
            .sameInstance(SortOrder::class.java, SortOrder.Ascending)
    }

    @Test
    fun `map options`() {
        val actual = sut.map(fixtFilterDomain, fixPageSize, fixtPage)

        assertEquals(fixPageSize, actual.options.limit)
        assertEquals(fixtPage, actual.options.page)
    }

    @Test
    fun `map page to 1 whn input null`() {
        fixtPage = null

        val actual = sut.map(fixtFilterDomain, fixPageSize, fixtPage)

        assertEquals(1, actual.options.page)
    }

    @Test
    fun `map all status`() {
        fixtFilterDomain = fixtFilterDomain.copy(status = Status.All)

        val actual = sut.map(fixtFilterDomain, fixPageSize, fixtPage)

        assertNull(actual.query.success)
    }

    @Test
    fun `map success status`() {
        fixtFilterDomain = fixtFilterDomain.copy(status = Status.Success)

        val actual = sut.map(fixtFilterDomain, fixPageSize, fixtPage)

        assert(actual.query.success!!)
    }

    @Test
    fun `map failure status`() {
        fixtFilterDomain = fixtFilterDomain.copy(status = Status.Failure)

        val actual = sut.map(fixtFilterDomain, fixPageSize, fixtPage)

        assertFalse(actual.query.success!!)
    }

    @Test
    fun `map ascending date`() {
        fixtFilterDomain = fixtFilterDomain.copy(dateSortOrder = SortOrder.Ascending)

        val actual = sut.map(fixtFilterDomain, fixPageSize, fixtPage)

        assertEquals(LaunchRequest.Options.Sort.SORT_ASCENDING, actual.options.sort!!.date_utc)
    }

    @Test
    fun `map descending date`() {
        fixtFilterDomain = fixtFilterDomain.copy(dateSortOrder = SortOrder.Descending)

        val actual = sut.map(fixtFilterDomain, fixPageSize, fixtPage)

        assertEquals(LaunchRequest.Options.Sort.SORT_DESCENDING, actual.options.sort!!.date_utc)
    }

    @Test
    fun `map year to date params when selected`() {
        val fixtYear: Int = fixture()
        fixtFilterDomain = fixtFilterDomain.copy(year = fixtYear)

        val actual = sut.map(fixtFilterDomain, fixPageSize, fixtPage)

        assertEquals(fixtYear, actual.query.date_utc!!.from)
        assertEquals(fixtYear + 1, actual.query.date_utc!!.to)
    }

    @Test
    fun `map year to null params when not selected`() {
        fixtFilterDomain = fixtFilterDomain.copy(year = null)

        val actual = sut.map(fixtFilterDomain, fixPageSize, fixtPage)

        assertNull(actual.query.date_utc)
    }


}
