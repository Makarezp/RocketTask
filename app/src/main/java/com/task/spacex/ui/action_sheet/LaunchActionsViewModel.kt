package com.task.spacex.ui.action_sheet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.task.spacex.repository.LaunchRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class LaunchActionsViewModel @AssistedInject constructor(
    private val launchRepository: LaunchRepository,
    @Assisted private val launchId: String
) : ViewModel() {



    companion object {
        fun provideFactory(
            assistedFactory: LaunchActionsViewModelFactory,
            launchId: String
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return assistedFactory.create(launchId) as T
            }
        }
    }
}

@AssistedFactory
interface LaunchActionsViewModelFactory {
    fun create(launchId: String): LaunchActionsViewModel
}
