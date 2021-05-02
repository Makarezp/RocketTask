package com.task.spacex.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.task.spacex.repository.api.ApiService
import com.task.spacex.repository.api.LaunchEntityMapper
import com.task.spacex.repository.api.LaunchRequestMapper
import com.task.spacex.repository.db.LaunchDao
import com.task.spacex.repository.db.LaunchEntity
import com.task.spacex.repository.db.PageKeyDao
import com.task.spacex.repository.db.SpaceXDatabase
import com.task.spacex.repository.domain.FilterDomain
import javax.inject.Inject

class PagerFactory @Inject constructor(
    private val apiService: ApiService,
    private val database: SpaceXDatabase,
    private val launchDao: LaunchDao,
    private val pageKeyDao: PageKeyDao,
    private val launchRequestMapper: LaunchRequestMapper,
    private val launchEntityMapper: LaunchEntityMapper
) {

    fun newPager(filterDomain: FilterDomain): Pager<Int, LaunchEntity> =
        Pager(
            config = PagingConfig(
                pageSize = 15,
                enablePlaceholders = false,
            ),
            remoteMediator = PagedSourceMediator(
                database,
                launchDao,
                pageKeyDao,
                apiService,
                launchRequestMapper,
                launchEntityMapper,
                filterDomain
            ),
            pagingSourceFactory = { launchDao.getLaunches() }
        )
}
