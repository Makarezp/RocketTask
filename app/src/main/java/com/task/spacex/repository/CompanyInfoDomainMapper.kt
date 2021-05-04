package com.task.spacex.repository

import com.task.spacex.repository.api.CompanyInfoResponse
import com.task.spacex.repository.domain.CompanyInfoDomain
import javax.inject.Inject

class CompanyInfoDomainMapper @Inject constructor() {

    fun map(response: CompanyInfoResponse): CompanyInfoDomain {
        return CompanyInfoDomain(
            response.name,
            response.founder,
            response.founded,
            response.employees,
            response.launchSites,
            response.valuation
        )
    }
}
