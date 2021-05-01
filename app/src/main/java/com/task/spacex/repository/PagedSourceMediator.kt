package com.task.spacex.repository

import androidx.paging.*
import androidx.room.withTransaction
import com.task.spacex.repository.db.*
import retrofit2.HttpException
import java.io.IOException

@ExperimentalPagingApi
class PagedSourceMediator(
    private val database: SpaceXDatabase,
    private val launchDao: LaunchDao,
    private val pagedKeyDao: PageKeyDao,
    private val apiService: ApiService,
    private val launchRequestMapper: LaunchRequestMapper,
    private val filterDomain: FilterDomain
) : RemoteMediator<Int, LaunchEntity>() {

    companion object {
        private const val LAUNCH_PAGE_ID = "launch"
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, LaunchEntity>
    ): MediatorResult {
        try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val remoteKey = database.withTransaction {
                        pagedKeyDao.pageKeyById(LAUNCH_PAGE_ID)
                    }
                    if (remoteKey.nextPage == null) {
                        return MediatorResult.Success(endOfPaginationReached = true)
                    }
                    remoteKey.nextPage
                }
            }

            val loadSize = state.config.pageSize
            val response =
                apiService.getLaunches(launchRequestMapper.map(filterDomain, loadSize, loadKey))
            val items = response.docs.map { LaunchEntity(it.id, it.name, it.links.patch.small) }
            val nextKey = response.nextPage

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    launchDao.deleteAll()
                    pagedKeyDao.deleteAll()
                }

                pagedKeyDao.insert(PageKeyEntity(LAUNCH_PAGE_ID, nextKey))
                launchDao.insertAll(items)
            }

            return MediatorResult.Success(endOfPaginationReached = items.isEmpty())
        } catch (e: IOException) {
            return MediatorResult.Error(e)
        } catch (e: HttpException) {
            return MediatorResult.Error(e)
        }
    }

}
