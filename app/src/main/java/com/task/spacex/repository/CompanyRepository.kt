package com.task.spacex.repository

import com.task.spacex.repository.api.ApiService
import com.task.spacex.repository.api.CompanyInfoDomainMapper
import com.task.spacex.repository.domain.CompanyInfoDomain
import javax.inject.Inject

class CompanyRepository @Inject constructor(
    private val apiService: ApiService,
    private val companyInfoDomainMapper: CompanyInfoDomainMapper
) {

    suspend fun getCompanyInfo(): CompanyInfoDomain {
        val response = apiService.getCompanyInfo()
        return companyInfoDomainMapper.map(response)
    }
}
