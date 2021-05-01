package com.task.spacex.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.task.spacex.repository.db.LaunchDao
import com.task.spacex.repository.db.LaunchEntity
import com.task.spacex.repository.db.PageKeyDao
import com.task.spacex.repository.db.SpaceXDatabase
import javax.inject.Inject

@ExperimentalPagingApi
class PagerFactory @Inject constructor(
    private val apiService: ApiService,
    private val database: SpaceXDatabase,
    private val launchDao: LaunchDao,
    private val pageKeyDao: PageKeyDao,
    private val launchRequestMapper: LaunchRequestMapper
) {

    fun newPager(filterDomain: FilterDomain): Pager<Int, LaunchEntity> =
        Pager(
            config = PagingConfig(
                pageSize = 40,
                enablePlaceholders = false,
                prefetchDistance = 20
            ),
            remoteMediator = PagedSourceMediator(
                database,
                launchDao,
                pageKeyDao,
                apiService,
                launchRequestMapper,
                filterDomain
            ),
            pagingSourceFactory = { launchDao.getLaunches() }
        )
}
