package com.task.spacex.ui.launch_list

import com.task.spacex.R
import com.task.spacex.UnitTestBase
import com.task.spacex.repository.domain.CompanyInfoDomain
import com.task.spacex.util.StringsWrapper
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.junit.Assert.assertEquals
import org.junit.Test

class CompanyItemUiMapperTest : UnitTestBase<CompanyItemUiMapper>() {

    @MockK
    private lateinit var strings: StringsWrapper

    override fun buildSut(): CompanyItemUiMapper {
        return CompanyItemUiMapper(strings)
    }

    @Test
    fun map() {
        val fixtText: String = fixture()
        val fixtCompanyInfo: CompanyInfoDomain = fixture()
        every {
            strings.resolve(
                R.string.company_info,
                fixtCompanyInfo.name,
                fixtCompanyInfo.founderName,
                fixtCompanyInfo.yearFounded.toString(),
                fixtCompanyInfo.employees.toString(),
                fixtCompanyInfo.launchSites.toString(),
                fixtCompanyInfo.valuation.toString()
            )
        } returns fixtText

        val actual = sut.map(fixtCompanyInfo)

        assertEquals(TextCell(fixtText), actual)
    }
}
