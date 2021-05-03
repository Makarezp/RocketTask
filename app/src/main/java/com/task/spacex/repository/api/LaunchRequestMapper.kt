package com.task.spacex.repository.api

import com.task.spacex.repository.api.LaunchRequest.Options
import com.task.spacex.repository.api.LaunchRequest.Options.Sort
import com.task.spacex.repository.api.LaunchRequest.Query
import com.task.spacex.repository.domain.FilterDomain
import javax.inject.Inject

class LaunchRequestMapper @Inject constructor() {

    fun map(filterDomain: FilterDomain, pageSize: Int, page: Int?): LaunchRequest {
        return LaunchRequest(
            Query(
                success = mapSuccessQuery(filterDomain),
                date_utc = mapDateRange(filterDomain.year)
            ),
            Options(
                limit = pageSize,
                page = page ?: 1,
                sort = Sort(
                    date_utc = mapSort(filterDomain.dateSortOrder)
                )
            )
        )
    }

    private fun mapDateRange(selectedYear: Int?): Query.DateUtcParams? {
        if (selectedYear == null) {
            return null
        }

        return Query.DateUtcParams(
            selectedYear,
            selectedYear + 1
        )
    }

    private fun mapSort(sortOrder: FilterDomain.SortOrder): String =
        when (sortOrder) {
            is FilterDomain.SortOrder.Ascending -> Sort.SORT_ASCENDING
            is FilterDomain.SortOrder.Descending -> Sort.SORT_DESCENDING
        }

    private fun mapSuccessQuery(filterDomain: FilterDomain): Boolean? =
        when (filterDomain.status) {
            is FilterDomain.Status.Success -> true
            is FilterDomain.Status.Failure -> false
            else -> null
        }
}
