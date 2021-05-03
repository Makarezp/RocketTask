package com.task.spacex.repository

import com.task.spacex.repository.api.ApiService
import com.task.spacex.repository.api.CompanyInfoDomainMapper
import com.task.spacex.repository.domain.CompanyDomain
import javax.inject.Inject

class CompanyRepository @Inject constructor(
    private val apiService: ApiService,
    private val companyInfoDomainMapper: CompanyInfoDomainMapper
) {

    suspend fun getCompany(): CompanyDomain {
        val response = apiService.getCompany()
        return companyInfoDomainMapper.map(response)
    }
}
