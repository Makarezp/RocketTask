package com.task.spacex.repository.api

import com.task.spacex.repository.domain.CompanyInfoDomain

class CompanyInfoDomainMapper {

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
