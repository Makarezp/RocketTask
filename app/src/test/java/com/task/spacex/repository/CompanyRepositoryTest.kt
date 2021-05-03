package com.task.spacex.repository

import com.task.spacex.UnitTestBase
import com.task.spacex.repository.api.ApiService
import com.task.spacex.repository.api.CompanyInfoDomainMapper
import com.task.spacex.repository.api.CompanyInfoResponse
import com.task.spacex.repository.domain.CompanyDomain
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Test

class CompanyRepositoryTest : UnitTestBase<CompanyRepository>() {

    @MockK
    private lateinit var mockApi: ApiService

    @MockK
    private lateinit var mockResponseMapper: CompanyInfoDomainMapper

    override fun buildSut(): CompanyRepository {
        return CompanyRepository(
            mockApi,
            mockResponseMapper
        )
    }

    @Test
    fun getCompanyInfo() = runBlockingTest {
        val fixtResponse: CompanyInfoResponse = fixture()
        val fixtCompany: CompanyDomain = fixture()
        coEvery { mockApi.getCompany() } returns fixtResponse
        coEvery { mockResponseMapper.map(fixtResponse) } returns fixtCompany

        val actual = sut.getCompany()

        assertEquals(fixtCompany, actual)
    }
}
