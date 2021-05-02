package com.task.spacex.ui.launch_list

import androidx.annotation.DrawableRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.task.spacex.repository.FilterRepository
import com.task.spacex.repository.domain.LaunchDomain
import com.task.spacex.repository.LaunchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class RocketListViewModel @Inject constructor(
    private val launchRepository: LaunchRepository,
    private val filterRepository: FilterRepository
) : ViewModel() {

    data class LaunchItemUiModel(
        val missionNameLabel: String,
        val dateAtTimeLabel: String,
        val daysToSinceLabel: String,
        val daysCountLabel: String,
        @DrawableRes val statusIcon: Int,
        val missionIconUrl: String?
    )

    fun getLaunches(): Flow<PagingData<LaunchDomain>> =
        filterRepository.getFilter()
            .distinctUntilChanged { old, new -> old == new }
            .flatMapLatest { filterDomain -> launchRepository.getLaunches(filterDomain) }
            .cachedIn(viewModelScope)
}
