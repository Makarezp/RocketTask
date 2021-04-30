package com.task.spacex.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.spacex.repository.ApiService
import com.task.spacex.repository.LaunchRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RocketListViewModel @Inject constructor(
        private val apiService: ApiService
) : ViewModel() {

    fun fetch() {
        viewModelScope.launch {
            val response = apiService.getLaunches(LaunchRequest(
                    LaunchRequest.Options(10)
            ))
            print(response)
        }
    }


}