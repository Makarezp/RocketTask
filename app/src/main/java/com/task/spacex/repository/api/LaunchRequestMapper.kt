package com.task.spacex.repository.api

import com.task.spacex.repository.domain.FilterDomain
import com.task.spacex.repository.api.LaunchRequest.*
import javax.inject.Inject

class LaunchRequestMapper @Inject constructor() {

    fun map(filterDomain: FilterDomain, pageSize: Int, page: Int?): LaunchRequest {
        return LaunchRequest(
            Query(
                success = mapSuccessQuery(filterDomain)
            ),
            Options(
                limit = pageSize,
                page = page ?: 1
            )
        )
    }

    private fun mapSuccessQuery(filterDomain: FilterDomain): Boolean? =
        when (filterDomain.status) {
            is FilterDomain.Status.Success -> true
            is FilterDomain.Status.Failure -> false
            else -> null
        }
}
