package com.task.spacex.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.spacex.repository.domain.FilterDomain
import com.task.spacex.repository.domain.FilterDomain.*
import com.task.spacex.repository.FilterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FilterDialogViewModel @Inject constructor(
    private val filterRepository: FilterRepository
) : ViewModel() {

    private var _currentFilter = filterRepository.getFilter().value

    private val _dismissAction = MutableSharedFlow<Unit>()
    var dismissAction =_dismissAction.asSharedFlow()

    // Simplification - view shouldn't be coupled with domain
    val currentFilter: FilterDomain
        get() = _currentFilter

    fun successStatusChecked() {
        _currentFilter = _currentFilter.copy(status = Status.Success)
    }

    fun failureStatusChecked() {
        _currentFilter = _currentFilter.copy(status = Status.Failure)
    }

    fun allStatusChecked() {
        _currentFilter = _currentFilter.copy(status = Status.All)
    }

    fun applyChanges() {
        filterRepository.setFilter(_currentFilter)
        viewModelScope.launch {
            _dismissAction.emit(Unit)
        }
    }
}
