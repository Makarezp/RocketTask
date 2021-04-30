package com.task.spacex.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class LaunchRepository @Inject constructor(
    private val launchPagingSource: LaunchPagingSource
) {

    fun getSearchResultStream(): Flow<PagingData<LaunchDomain>> {
        return Pager(
            config = PagingConfig(
                pageSize = 40,
                enablePlaceholders = false,
                prefetchDistance = 20
            ),
            pagingSourceFactory = { launchPagingSource }
        ).flow
    }
}
