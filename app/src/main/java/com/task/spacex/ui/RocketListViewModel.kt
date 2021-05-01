package com.task.spacex.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.task.spacex.repository.FilterRepository
import com.task.spacex.repository.domain.LaunchDomain
import com.task.spacex.repository.LaunchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class RocketListViewModel @Inject constructor(
    private val launchRepository: LaunchRepository,
    private val filterRepository: FilterRepository
) : ViewModel() {

    fun fetch(): Flow<PagingData<LaunchDomain>> =
        filterRepository.getFilter()
            .flatMapLatest { filterDomain -> launchRepository.getLaunches(filterDomain) }
            .cachedIn(viewModelScope)
}
