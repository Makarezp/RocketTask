package com.task.spacex.repository

import androidx.paging.*
import com.task.spacex.repository.db.LaunchEntity
import com.task.spacex.repository.domain.FilterDomain
import com.task.spacex.repository.domain.LaunchDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class LaunchRepository @Inject constructor(
    private val pagerFactory: PagerFactory,
    private val launchDomainMapper: LaunchDomainMapper
) {

    fun getLaunches(filterDomain: FilterDomain): Flow<PagingData<LaunchDomain>> =
        pagerFactory.newPager(filterDomain)
            .flow
            .map { data -> mapPagingData(data) }

    private fun mapPagingData(data: PagingData<LaunchEntity>) =
        data.map { entity -> launchDomainMapper.map(entity) }

}
