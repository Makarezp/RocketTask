package com.task.spacex.ui.action_sheet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.task.spacex.repository.LaunchRepository
import com.task.spacex.repository.domain.LaunchDomain
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch


class LaunchActionsViewModel @AssistedInject constructor(
    @Assisted private val launchId: String,
    private val launchRepository: LaunchRepository,
) : ViewModel() {

    private val _openLinkAction = MutableSharedFlow<String>()
    var openLinkAction = _openLinkAction.asSharedFlow()

    fun onArticleClicked() {
        emitLink {
            it.article
        }
    }

    fun onVideoClicked() {
        emitLink {
            it.webcast
        }
    }

    fun onWikiClicked() {
        emitLink {
            it.wikipedia
        }
    }

    private fun emitLink(linkProvider: (LaunchDomain) -> String?) {
        viewModelScope.launch {
            val domain = launchRepository.getLaunch(launchId)
            val link = linkProvider(domain)
            if (link != null) {
                _openLinkAction.emit(link)
            }
        }
    }

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
