package com.task.spacex.ui.launch_list

import com.task.spacex.R
import com.task.spacex.repository.domain.CompanyDomain
import com.task.spacex.util.StringsWrapper
import javax.inject.Inject

class CompanyItemUiMapper @Inject constructor(
    private val strings: StringsWrapper
) {

    fun map(company: CompanyDomain): TextCell {
        return TextCell(
            strings.resolve(
                R.string.company_info,
                company.name,
                company.founderName,
                company.yearFounded.toString(),
                company.employees.toString(),
                company.launchSites.toString(),
                company.valuation.toString()
            )
        )
    }
}
