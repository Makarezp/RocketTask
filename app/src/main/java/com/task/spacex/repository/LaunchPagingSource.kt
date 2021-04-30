package com.task.spacex.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

private const val INITIAL_PAGE_NUMBER = 1

class LaunchPagingSource @Inject constructor(
    private val service: ApiService
) : PagingSource<Int, LaunchDomain>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LaunchDomain> {
        return try {
            val key = params.key ?: INITIAL_PAGE_NUMBER
            val response = service.getLaunches(LaunchRequest(
                LaunchRequest.Options(params.loadSize, key)
            ))
            val launches = response.docs.map { LaunchDomain(it.id, it.name, it.links.patch.small) }
            val nextKey = response.nextPage
            LoadResult.Page(
                data = launches,
                prevKey = response.prevPage,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, LaunchDomain>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }



}