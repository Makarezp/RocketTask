package com.task.spacex.repository.api

import com.task.spacex.UnitTestBase
import com.task.spacex.repository.CompanyInfoDomainMapper
import org.junit.Assert.assertEquals
import org.junit.Test

class CompanyInfoDomainMapperTest : UnitTestBase<CompanyInfoDomainMapper>() {

    override fun buildSut(): CompanyInfoDomainMapper {
        return CompanyInfoDomainMapper()
    }

    @Test
    fun map() {
        val fixtResponse: CompanyInfoResponse = fixture()

        val actual = sut.map(fixtResponse)

        assertEquals(fixtResponse.name, actual.name)
        assertEquals(fixtResponse.founder, actual.founderName)
        assertEquals(fixtResponse.founded, actual.yearFounded)
        assertEquals(fixtResponse.employees, actual.employees)
        assertEquals(fixtResponse.launchSites, actual.launchSites)
        assertEquals(fixtResponse.valuation, actual.valuation)
    }
}
