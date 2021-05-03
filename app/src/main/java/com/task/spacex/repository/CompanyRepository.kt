package com.task.spacex.repository

import com.task.spacex.repository.api.ApiService
import com.task.spacex.repository.api.CompanyInfoDomainMapper
import com.task.spacex.repository.domain.CompanyDomain
import javax.inject.Inject

class CompanyRepository @Inject constructor(
    private val apiService: ApiService,
    private val companyInfoDomainMapper: CompanyInfoDomainMapper
) {

    suspend fun getCompanyInfo(): CompanyDomain {
        val response = apiService.getCompanyInfo()
        return companyInfoDomainMapper.map(response)
    }
}
