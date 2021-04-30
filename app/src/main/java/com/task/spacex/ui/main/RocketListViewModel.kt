package com.task.spacex.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.task.spacex.repository.ApiService
import com.task.spacex.repository.LaunchDomain
import com.task.spacex.repository.LaunchRepository
import com.task.spacex.repository.LaunchRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RocketListViewModel @Inject constructor(
    private val launchRepository: LaunchRepository
) : ViewModel() {

    fun fetch(): Flow<PagingData<LaunchDomain>> {
        return launchRepository.getSearchResultStream().cachedIn(viewModelScope)
    }
}
