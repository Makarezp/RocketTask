package com.task.spacex.repository

import com.task.spacex.repository.domain.FilterDomain
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Simple in memory filter state store - easy to extend later if filtering needs to be persisted
 * between app launches
 */
@Singleton
class FilterRepository @Inject constructor() {

    private val filterState = MutableStateFlow(FilterDomain.DEFAULT_FILTER_VALUES)

    fun getFilter(): StateFlow<FilterDomain> =
        filterState

    fun setFilter(filterDomain: FilterDomain) {
        filterState.value = filterDomain
    }
}
