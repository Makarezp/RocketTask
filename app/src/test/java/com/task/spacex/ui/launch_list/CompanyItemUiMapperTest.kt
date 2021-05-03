package com.task.spacex.ui.launch_list

import com.task.spacex.R
import com.task.spacex.UnitTestBase
import com.task.spacex.repository.domain.CompanyDomain
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
        val fixtCompany: CompanyDomain = fixture()
        every {
            strings.resolve(
                R.string.company_info,
                fixtCompany.name,
                fixtCompany.founderName,
                fixtCompany.yearFounded.toString(),
                fixtCompany.employees.toString(),
                fixtCompany.launchSites.toString(),
                fixtCompany.valuation.toString()
            )
        } returns fixtText

        val actual = sut.map(fixtCompany)

        assertEquals(TextCell(fixtText), actual)
    }
}
