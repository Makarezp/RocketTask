package com.task.spacex.repository

import com.task.spacex.UnitTestBase
import com.task.spacex.repository.domain.FilterDomain
import org.junit.Assert.*
import org.junit.Test

class FilterRepositoryTest : UnitTestBase<FilterRepository>() {

    override fun buildSut(): FilterRepository {
        return FilterRepository()
    }

    override fun customiseFixture() {
        fixture.jFixture.customise()
            .sameInstance(FilterDomain.Status::class.java, FilterDomain.Status.Success)
    }

    @Test
    fun `default filter`() {
        val actual = sut.getFilter().value

        assertEquals(FilterDomain.DEFAULT_FILTER_VALUES, actual)
    }

    @Test
    fun `set filter`() {
        val fixtFilterDomain: FilterDomain = fixture()

        sut.setFilter(fixtFilterDomain)

        assertEquals(fixtFilterDomain, sut.getFilter().value)
    }
}
