package com.task.spacex.repository

import androidx.paging.PagingData
import androidx.paging.map
import com.task.spacex.repository.db.LaunchDao
import com.task.spacex.repository.db.LaunchEntity
import com.task.spacex.repository.domain.FilterDomain
import com.task.spacex.repository.domain.LaunchDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class LaunchRepository @Inject constructor(
    private val pagerFactory: PagerFactory,
    private val launchDomainMapper: LaunchDomainMapper,
    private val launchDao: LaunchDao
) {

    suspend fun getLaunch(id: String): LaunchDomain {
        val launch = launchDao.getLaunchById(id)
        return launchDomainMapper.map(launch)
    }

    fun getLaunches(filterDomain: FilterDomain): Flow<PagingData<LaunchDomain>> =
        pagerFactory.newPager(filterDomain)
            .flow
            .map { data -> mapPagingData(data) }

    private fun mapPagingData(data: PagingData<LaunchEntity>) =
        data.map { entity -> launchDomainMapper.map(entity) }

}
