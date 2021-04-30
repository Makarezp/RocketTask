package com.task.spacex.repository

import javax.inject.Inject

class LaunchPagingSourceFactory @Inject constructor(
    private val apiService: ApiService,
    private val launchRequestMapper: LaunchRequestMapper
) {
    fun newSource(filterDomain: FilterDomain): LaunchPagingSource {
        return LaunchPagingSource(filterDomain, apiService, launchRequestMapper)
    }
}