package com.task.spacex.repository.api

import com.task.spacex.repository.domain.CompanyDomain
import javax.inject.Inject

class CompanyInfoDomainMapper @Inject constructor() {

    fun map(response: CompanyInfoResponse): CompanyDomain {
        return CompanyDomain(
            response.name,
            response.founder,
            response.founded,
            response.employees,
            response.launchSites,
            response.valuation
        )
    }
}
