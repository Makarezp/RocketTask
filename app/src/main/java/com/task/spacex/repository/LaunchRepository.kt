package com.task.spacex.repository

import androidx.paging.*
import com.task.spacex.repository.db.LaunchEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


@ExperimentalPagingApi
class LaunchRepository @Inject constructor(
    private val pagerFactory: PagerFactory,
) {

    fun getLaunches(filterDomain: FilterDomain): Flow<PagingData<LaunchDomain>> =
        pagerFactory.newPager(filterDomain)
            .flow
            .map { data -> mapPagingData(data) }

    private fun mapPagingData(data: PagingData<LaunchEntity>) =
        data.map { entity ->
            LaunchDomain(
                entity.id,
                entity.rocket,
                entity.patchUrl
            )
        }

}
