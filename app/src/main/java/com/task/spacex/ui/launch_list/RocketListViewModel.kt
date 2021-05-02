package com.task.spacex.ui.launch_list

import androidx.annotation.DrawableRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.task.spacex.repository.FilterRepository
import com.task.spacex.repository.LaunchRepository
import com.task.spacex.repository.domain.LaunchDomain
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class RocketListViewModel @Inject constructor(
    private val launchRepository: LaunchRepository,
    private val filterRepository: FilterRepository,
    private val launchItemUiMapper: LaunchItemUiMapper
) : ViewModel() {

    data class LaunchItemUiModel(
        val missionNameLabel: String,
        val dateAtTimeLabel: String,
        val daysToSinceLabel: String,
        val daysCountLabel: String,
        @DrawableRes val statusIcon: Int,
        val missionIconUrl: String?,
        val domain: LaunchDomain
    )

    fun getLaunches(): Flow<PagingData<LaunchItemUiModel>> =
        filterRepository.getFilter()
            .distinctUntilChanged { old, new -> old == new }
            .flatMapLatest { filterDomain -> launchRepository.getLaunches(filterDomain) }
            .map { data ->
                data.map { launch ->
                    launchItemUiMapper.map(launch)
                }
            }
            .cachedIn(viewModelScope)
}
