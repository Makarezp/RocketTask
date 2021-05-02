package com.task.spacex.ui.launch_list

import androidx.annotation.DrawableRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.task.spacex.repository.FilterRepository
import com.task.spacex.repository.LaunchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RocketListViewModel @Inject constructor(
    private val launchRepository: LaunchRepository,
    private val filterRepository: FilterRepository,
    private val launchItemUiMapper: LaunchItemUiMapper
) : ViewModel() {

    data class LaunchItemUiModel(
        val id: String,
        val missionName: String,
        val dateAtTime: String,
        val daysToSince: String,
        val daysCount: String,
        @DrawableRes val statusIcon: Int,
        val missionIconUrl: String?,
    )

    private val _openSheetAction = MutableSharedFlow<String>()
    var openSheetAction =_openSheetAction.asSharedFlow()

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

    fun itemClicked(id: String) {
        viewModelScope.launch {
            _openSheetAction.emit(id)
        }
    }
}
